package slipp.controller.handleradapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RedirectView;
import nextstep.mvc.tobe.RequestForwardingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerAdaptor implements HandlerAdapter {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private ControllerAdaptor() {
    }

    private static class SingletonHolder {
        private static final ControllerAdaptor INSTANCE = new ControllerAdaptor();
    }

    public static ControllerAdaptor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean supports(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Controller controller = (Controller) handler;

        String viewName = controller.execute(request, response);

        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new ModelAndView(RedirectView.from(viewName.substring(DEFAULT_REDIRECT_PREFIX.length())));
        }
        return new ModelAndView(RequestForwardingView.from(viewName));
    }
}

