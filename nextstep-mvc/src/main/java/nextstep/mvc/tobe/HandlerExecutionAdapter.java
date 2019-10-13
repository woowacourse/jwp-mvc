package nextstep.mvc.tobe;

import nextstep.mvc.exception.UnsupportedViewException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionAdaptor implements HandlerAdaptor {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        Object view = ((HandlerExecution) handler).handle(req, resp);
        return (ModelAndView) view;
    }
}
