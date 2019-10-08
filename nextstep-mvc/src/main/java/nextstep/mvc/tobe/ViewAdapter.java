package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;

public class ViewAdapter {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    public static ModelAndView render(Object view) {
        if (view instanceof String) {
            if (((String) view).startsWith(DEFAULT_REDIRECT_PREFIX)) {
                return new ModelAndView(new RedirectView(((String) view).substring(DEFAULT_REDIRECT_PREFIX.length())));
            }
            return new ModelAndView(new JspView((String) view));
        } else if (view instanceof View) {
            return new ModelAndView((View) view);
        }
        return (ModelAndView) view;
    }
}
