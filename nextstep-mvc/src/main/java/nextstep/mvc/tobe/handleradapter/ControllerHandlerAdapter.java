package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(final Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        final String viewName = ((Controller) handler).execute(request, response);
        return new ModelAndView(viewName);
    }
}
