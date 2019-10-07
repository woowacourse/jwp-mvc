package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;

public class ViewHandler {

    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    public static ModelAndView handle(Object view) throws Exception {
        if (view instanceof View) {
            return new ModelAndView(conversion(view));
        }
        if (view instanceof String) {
            return new ModelAndView(processing(view));
        }
        return new ModelAndView(new JspView("/err/404.jsp"));
    }

    private static View conversion(Object view) {
        return (View) view;
    }

    private static View processing(Object view) {
        String name = (String) view;
        if (name.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(name.substring(DEFAULT_REDIRECT_PREFIX.length()));
        }
        return new JspView(name);
    }
}
