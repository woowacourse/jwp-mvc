package nextstep.mvc.resolver;

import nextstep.mvc.view.RedirectView;
import nextstep.mvc.view.View;

public class RedirectViewResolver implements ViewResolver {
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean supports(String viewName) {
        return viewName.startsWith(REDIRECT_PREFIX);
    }

    @Override
    public View resolveViewName(String viewName) {
        return new RedirectView(viewName);
    }
}
