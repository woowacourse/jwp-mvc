package nextstep.mvc.tobe;

import nextstep.mvc.asis.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleControllerAdapter implements HandlerAdapter {

    @Override
    public ModelAndView handle(final HttpServletRequest req, final HttpServletResponse resp, final Object handler) throws Exception {
        final Controller controller = (Controller) handler;
        final String viewName = String.valueOf((controller.execute(req, resp)));
        return new ModelAndView(viewName);
    }

    @Override
    public boolean supports(final Object handler) {
        return handler instanceof Controller;
    }
}
