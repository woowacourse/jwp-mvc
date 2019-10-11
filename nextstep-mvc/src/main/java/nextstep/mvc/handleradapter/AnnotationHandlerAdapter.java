package nextstep.mvc.handleradapter;

import nextstep.mvc.argumentresolver.*;
import nextstep.mvc.exception.NotFoundArgumentResolverException;
import nextstep.mvc.handlermapping.HandlerExecution;
import nextstep.mvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationHandlerAdapter implements HandlerAdapter {
    private final List<ArgumentResolver> argumentResolvers = Arrays.asList(new ServletArgumentResolver(),
            new RequestParamArgumentResolver(),
            new ModelAttributeArgumentResolver());

    @Override
    public boolean canHandle(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerExecution castHandler = (HandlerExecution) handler;
        List<MethodParameter> methodParameters = castHandler.extractMethodParameters();

        List<Object> values = collectValues(request, response, methodParameters);
        Object result = castHandler.handle(values.toArray());

        if (result instanceof ModelAndView) {
            return (ModelAndView) result;
        }
        String viewName = (String) result;
        return new ModelAndView(viewName);
    }

    private List<Object> collectValues(HttpServletRequest request, HttpServletResponse response, List<MethodParameter> methodParameters) {
        return methodParameters.stream()
                .map(methodParameter -> resolveArgument(request, response, methodParameter))
                .collect(Collectors.toList());
    }

    private Object resolveArgument(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        ArgumentResolver argumentResolver = getArgumentResolver(methodParameter);
        return argumentResolver.resolve(request, response, methodParameter);
    }

    private ArgumentResolver getArgumentResolver(MethodParameter methodParameter) {
        return argumentResolvers.stream()
                .filter(ar -> ar.canResolve(methodParameter))
                .findAny()
                .orElseThrow(NotFoundArgumentResolverException::new);
    }
}