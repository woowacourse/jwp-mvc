package nextstep.mvc.tobe.handlerAdapter;

import nextstep.mvc.MethodParameter;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.argumentResolver.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestHandlerAdapter implements HandlerAdapter {

    Set<HandlerMethodArgumentResolver> argumentResolvers;

    @Override
    public boolean canAdapt(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public void setResolver(Set<HandlerMethodArgumentResolver> argumentResolvers) {
        this.argumentResolvers = argumentResolvers;
    }

    @Override
    public ModelAndView handleInternal(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerExecution handlerExecution = (HandlerExecution) handler;
        List<MethodParameter> methodParameters = createMethodParameters(handlerExecution);

        Object[] resolvedArguments = methodParameters.stream()
                .map(methodParameter -> resolve(methodParameter, request, response))
                .toArray();

        Object result = handlerExecution.handle(resolvedArguments);

        if (result instanceof String) {
            return new ModelAndView((String) result);
        }
        return (ModelAndView) result;
    }

    private List<MethodParameter> createMethodParameters(HandlerExecution handlerExecution) {
        Method method = handlerExecution.getMethod();
        Parameter[] parameters = method.getParameters();

        return Arrays.asList(parameters).stream()
                .map(x -> new MethodParameter(method, x))
                .collect(Collectors.toList());
    }

    private Object resolve(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        return argumentResolvers.stream()
                .filter(resolver -> resolver.supportsParameter(methodParameter))
                .map(resolver -> resolver.resolveArgument(methodParameter, request, response))
                .findAny()
                .orElseThrow(ArgumentResolveException::new);
    }
}

