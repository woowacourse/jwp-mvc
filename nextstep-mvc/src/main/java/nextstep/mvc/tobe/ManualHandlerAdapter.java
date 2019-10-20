package nextstep.mvc.tobe;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.view.JspView;
import nextstep.mvc.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManualHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView run(Object handler, HttpServletRequest req, HttpServletResponse res) throws Exception {
        final String path = ((nextstep.mvc.asis.Controller) handler).execute(req, res);
        return (path.startsWith(RedirectView.REDIRECT_PREFIX))
                ? new ModelAndView(new RedirectView(path))
                : new ModelAndView(new JspView(path));
    }
}