package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;

public class RedirectViewResolver implements ViewResolver {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean supports(String viewName) {
        return viewName != null && viewName.startsWith(DEFAULT_REDIRECT_PREFIX);
    }

    @Override
    public View resolve(String viewName) {
        return new RedirectView(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
    }
}
