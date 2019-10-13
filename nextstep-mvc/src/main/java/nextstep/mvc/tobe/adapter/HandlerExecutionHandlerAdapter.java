package nextstep.mvc.tobe.adapter;

import nextstep.mvc.HandlerAdapter;
import nextstep.mvc.tobe.handler.HandlerExecution;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        return ((HandlerExecution) handler).execute(req, resp);
    }
}
