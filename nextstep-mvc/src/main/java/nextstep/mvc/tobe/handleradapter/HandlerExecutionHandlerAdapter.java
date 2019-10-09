package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Object handleResult = ((HandlerExecution) handler).handle(req, resp);

        if (handleResult instanceof String) {
            String viewName = (String) handleResult;
            return new ModelAndView(new JspView(viewName));
        }

        return (ModelAndView) handleResult;
    }
}
