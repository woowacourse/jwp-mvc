package nextstep.mvc.tobe.adapter;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.WebRequest;
import nextstep.mvc.tobe.handler.HandlerExecution;
import nextstep.mvc.tobe.resolver.HandlerMethodArgumentResolverComposite;
import nextstep.mvc.tobe.resolver.MethodParameters;

public class HandlerExecutionAdapter implements HandlerAdapter {
    private HandlerMethodArgumentResolverComposite argumentResolvers;

    public HandlerExecutionAdapter() {
        argumentResolvers = HandlerMethodArgumentResolverComposite.getInstance();
    }

    @Override
    public ModelAndView handle(final WebRequest webRequest, final Object handler) throws Exception {
        final HandlerExecution handlerExecution = (HandlerExecution) handler;
        final MethodParameters methodParameters = new MethodParameters(handlerExecution.getMethod());

        final Object[] values = methodParameters.getMethodParams().stream()
                .map(parameter -> argumentResolvers.resolveArgument(webRequest, parameter))
                .toArray();

        final Object result = handlerExecution.execute(values);

        return parseModelAndView(result);
    }

    private ModelAndView parseModelAndView(final Object result) {
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