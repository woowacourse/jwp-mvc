package nextstep.mvc.tobe.handlerAdapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.View;
import nextstep.mvc.tobe.view.ForwardView;
import nextstep.mvc.tobe.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LegacyHandlerAdapter implements HandlerAdapter {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean canAdapt(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView adapt(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Controller controller = (Controller) handler;
        String viewName = controller.execute(req, resp);
        return new ModelAndView(createView(viewName));
    }

    public View createView(String viewName){
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
        }
        return new ForwardView(viewName);
    }
}
