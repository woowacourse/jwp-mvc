package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.HandlerMethod;
import nextstep.mvc.tobe.MethodParameter;
import nextstep.mvc.tobe.RequestContext;
import nextstep.mvc.tobe.argumentresolver.HandlerMethodArgumentResolver;
import nextstep.mvc.tobe.view.ModelAndView;

import java.util.ArrayList;
import java.util.List;

public class RequestMappingHandlerAdapter implements HandlerAdapter {
    private List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerMethod;
    }

    @Override
    public boolean hasArgumentResolvers() {
        return true;
    }

    @Override
    public ModelAndView handle(RequestContext requestContext, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        List<MethodParameter> methodParameters = handlerMethod.getMethodParameters();

        Object[] resolvedArguments = methodParameters.stream()
                .map(methodParameter -> resolveParameter(methodParameter, requestContext))
                .toArray();

        Object returnValue = handlerMethod.invoke(resolvedArguments);

        if (returnValue instanceof String) {
            return new ModelAndView((String) returnValue);
        }

        return (ModelAndView) returnValue;
    }

    private Object resolveParameter(MethodParameter methodParameter, RequestContext requestContext) {
        HandlerMethodArgumentResolver argumentResolver = argumentResolvers.stream()
                .filter(resolver -> resolver.supports(methodParameter))
                .findAny()
                .orElseThrow();
        return argumentResolver.resolve(requestContext, methodParameter);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        this.argumentResolvers.addAll(argumentResolvers);
    }
}
