package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;

public class RedirectViewResolver implements ViewResolver {
    static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean supports(final Object view) {
        if (view instanceof RedirectView) {
            return true;
        }
        if (view instanceof String) {
            return String.valueOf(view).startsWith(DEFAULT_REDIRECT_PREFIX);
        }
        return false;
    }

    @Override
    public View resolveView(final Object view) {
        if (view instanceof RedirectView) {
            return (RedirectView) view;
        }
        final String viewName = String.valueOf(view).substring(DEFAULT_REDIRECT_PREFIX.length());
        return new RedirectView(viewName);
    }
}
