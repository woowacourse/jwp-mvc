package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.argumentresolver.ArgumentResolvers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionHandlerAdapter implements HandlerAdapter {
    private final ArgumentResolvers argumentResolvers;

    public HandlerExecutionHandlerAdapter(ArgumentResolvers argumentResolvers) {
        this.argumentResolvers = argumentResolvers;
    }

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HandlerExecution handlerExecution = (HandlerExecution) handler;
        Object[] arguments = argumentResolvers.resolve(handlerExecution.getMethodParameters(), req, resp);
        Object handleResult = handlerExecution.handle(arguments);

        if (handleResult instanceof ModelAndView) {
            return (ModelAndView) handleResult;
        }

        return new ModelAndView(handleResult);
    }
}
