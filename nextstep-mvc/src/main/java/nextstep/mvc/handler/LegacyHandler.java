package nextstep.mvc.handler;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.view.JspView;
import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.RedirectView;
import nextstep.mvc.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LegacyHandler extends AbstractHandlerAdapter {
    private static final String PREFIX_REDIRECTION = "redirect:";

    public LegacyHandler(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String viewName = ((Controller) handler).execute(req, resp);
        return new ModelAndView(convertView(viewName));
    }

    private View convertView(String path) {
        return path.startsWith(PREFIX_REDIRECTION) ?
                new RedirectView(path) : new JspView(path);
    }

}
