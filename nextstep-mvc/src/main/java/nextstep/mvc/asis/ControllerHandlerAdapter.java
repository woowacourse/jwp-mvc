package nextstep.mvc.asis;

import nextstep.mvc.HandlerAdapter;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String forward = ((Controller) handler).execute(request, response);
        return new ModelAndView(new JspView(forward));
    }
}
