package nextstep.mvc.tobe.adapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerAdapter implements HandlerAdapter {

    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String JSP_SUFFIX = ".jsp";

    @Override
    public boolean support(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        Controller controller = (Controller) handler;
        String view = controller.execute(req, resp);
        View resolvedView = null;
        if (view.contains(JSP_SUFFIX)) {
            resolvedView = new JspView(view.substring(0, view.indexOf(".jsp")));
        }
        if (view.contains(REDIRECT_PREFIX)) {
            resolvedView = new RedirectView(view.substring(view.indexOf("redirect:")));
        }
        return new ModelAndView(resolvedView);
    }
}
