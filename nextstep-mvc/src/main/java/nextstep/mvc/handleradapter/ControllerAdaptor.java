package nextstep.mvc.handleradapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RedirectView;
import nextstep.mvc.tobe.RequestForwardingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerAdaptor implements Handler {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final Controller controller;

    private ControllerAdaptor(Controller handler) {
        this.controller = handler;
    }

    public static ControllerAdaptor from(Controller controller) {
        return new ControllerAdaptor(controller);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = controller.execute(request, response);

        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new ModelAndView(RedirectView.from(viewName.substring(DEFAULT_REDIRECT_PREFIX.length())));
        }
        return new ModelAndView(RequestForwardingView.from(viewName));
    }
}
