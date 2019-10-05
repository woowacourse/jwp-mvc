package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleControllerHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String viewName = ((Controller) handler).execute(req, resp);
        return new ModelAndView(new JspView(viewName));
    }
}
