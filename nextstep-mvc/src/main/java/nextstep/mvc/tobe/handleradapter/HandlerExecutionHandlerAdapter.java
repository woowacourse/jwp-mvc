package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(final Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        return ((HandlerExecution) handler).handle(request, response);
    }
}
