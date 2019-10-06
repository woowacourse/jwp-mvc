package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionAdapter implements HandlerAdapter {
    private HandlerMethodArgumentResolverComposite argumentResolvers;

    public HandlerExecutionAdapter() {
        argumentResolvers = new HandlerMethodArgumentResolverComposite();
    }

    @Override
    public ModelAndView handle(final HttpServletRequest req, final HttpServletResponse resp, final Object handler) throws Exception {
        final HandlerExecution handlerExecution = (HandlerExecution) handler;

        final MethodParameters methodParameters = new MethodParameters(handlerExecution.getMethod());

        final Object[] values = methodParameters.getMethodParams().stream()
                .map(parameter -> {
                    if (parameter.isSameType(HttpServletRequest.class)) {
                        return req;
                    }
                    if (parameter.isSameType(HttpServletResponse.class)) {
                        return resp;
                    }
                    return argumentResolvers.resolveArgument(req, parameter, handlerExecution.getMethod());
                })
                .toArray();

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