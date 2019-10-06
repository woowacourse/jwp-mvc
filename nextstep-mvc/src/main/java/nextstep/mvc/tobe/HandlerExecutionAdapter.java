package nextstep.mvc.tobe;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class HandlerExecutionAdapter implements HandlerAdapter {
    private HandlerMethodArgumentResolverComposite argumentResolvers;
    private ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    public HandlerExecutionAdapter() {
        argumentResolvers = new HandlerMethodArgumentResolverComposite();
    }

    @Override
    public ModelAndView handle(final HttpServletRequest req, final HttpServletResponse resp, final Object handler) throws Exception {
        final HandlerExecution handlerExecution = (HandlerExecution) handler;

        final Parameter[] parameters = handlerExecution.getParameters();
        final String[] names = nameDiscoverer.getParameterNames(handlerExecution.getMethod());
        final Object[] values = new Object[parameters.length];

        for (int i = 0; i < values.length; i++) {
            if (parameters[i].getType().equals(HttpServletRequest.class)) {
                values[i] = req;
            } else if (parameters[i].getType().equals(HttpServletResponse.class)) {
                values[i] = resp;
            } else {
                final MethodParameter methodParameter = new MethodParameter(parameters[i], names[i], i);
                values[i] = argumentResolvers.resolveArgument(req, methodParameter);
            }
        }

        final Object result = handlerExecution.execute(values);

        // view
        if (result instanceof String) {
            return new ModelAndView(String.valueOf(result));
        }
        return (ModelAndView) result;
    }

    @Override
    public boolean supports(final Object handler) {
        return handler instanceof HandlerExecution;
    }
}