package nextstep.mvc.tobe.adapter;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.mapping.HandlerExecution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return (ModelAndView) ((HandlerExecution) handler).handle(request, response);
    }
}
