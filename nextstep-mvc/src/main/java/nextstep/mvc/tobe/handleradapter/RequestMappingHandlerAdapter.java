package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.HandlerMethod;
import nextstep.mvc.tobe.MethodParameter;
import nextstep.mvc.tobe.RequestContext;
import nextstep.mvc.tobe.RequestContextKey;
import nextstep.mvc.tobe.argumentresolver.HandlerMethodArgumentResolver;
import nextstep.mvc.tobe.returnvaluehandler.HandlerMethodReturnValueHandler;
import nextstep.mvc.tobe.view.Model;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class RequestMappingHandlerAdapter implements HandlerAdapter {
    private List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
    private List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<>();

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerMethod;
    }

    @Override
    public boolean hasResolvers() {
        return true;
    }

    @Override
    public ModelAndView handle(RequestContext requestContext, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        List<MethodParameter> methodParameters = handlerMethod.getMethodParameters();
        Object[] resolvedArguments = getResolvedArguments(requestContext, methodParameters);
        Object returnValue = handlerMethod.invoke(resolvedArguments);
        postHandle(requestContext, handlerMethod, returnValue);

        return getModelAndView(requestContext, returnValue);
    }

    private void postHandle(RequestContext requestContext, HandlerMethod handlerMethod, Object returnValue) {
        returnValueHandlers.stream()
                .filter(returnValueHandler -> returnValueHandler.supports(handlerMethod))
                .forEach(returnValueHandler -> returnValueHandler.handle(requestContext, returnValue));
    }

    private ModelAndView getModelAndView(RequestContext requestContext, Object returnValue) {
        if (requestContext.isHandled()) {
            return null;
        }

        setAttributes(requestContext);

        if (returnValue instanceof String) {
            return new ModelAndView((String) returnValue);
        }

        if (returnValue instanceof View) {
            return new ModelAndView((View) returnValue);
        }

        if (returnValue instanceof ModelAndView) {
            return (ModelAndView) returnValue;
        }

        throw new ReturnValueCastFailedException();
    }

    private void setAttributes(RequestContext requestContext) {
        Model model = (Model) requestContext.getAttribute(RequestContextKey.MODEL.getKey());
        HttpServletRequest httpServletRequest = requestContext.getHttpServletRequest();
        model.getModelMap().forEach(httpServletRequest::setAttribute);
    }

    private Object[] getResolvedArguments(RequestContext requestContext, List<MethodParameter> methodParameters) {
        return methodParameters.stream()
                .map(methodParameter -> resolveParameter(methodParameter, requestContext))
                .toArray();
    }

    private Object resolveParameter(MethodParameter methodParameter, RequestContext requestContext) {
        HandlerMethodArgumentResolver argumentResolver = argumentResolvers.stream()
                .filter(resolver -> resolver.supports(methodParameter))
                .findAny()
                .orElseThrow(ArgumentResolverNotFoundException::new);

        return argumentResolver.resolve(requestContext, methodParameter);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        this.argumentResolvers.addAll(argumentResolvers);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        this.returnValueHandlers.addAll(returnValueHandlers);
    }
}
