package nextstep.mvc.tobe;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.view.JspView;
import nextstep.mvc.view.RedirectView;

public class HandlerAdapter {
    public Handler convert(Object handler) {
        if (handler instanceof Controller) {
            return (req, res) -> {
                final String path = ((Controller) handler).execute(req, res);
                if (path.startsWith(RedirectView.REDIRECT_PREFIX)) {
                    return new ModelAndView(new RedirectView(path));
                }
                return new ModelAndView(new JspView(path));
            };
        }
        if (handler instanceof Handler) {
            return (Handler) handler;
        }
        return null;
    }
}