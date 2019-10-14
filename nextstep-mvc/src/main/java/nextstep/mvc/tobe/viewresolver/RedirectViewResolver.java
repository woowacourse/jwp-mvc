package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;
import org.apache.commons.lang3.StringUtils;

public class RedirectViewResolver implements ViewResolver {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean supports(String viewName) {
        return !StringUtils.isEmpty(viewName) &&
                viewName.startsWith(DEFAULT_REDIRECT_PREFIX);
    }

    @Override
    public View resolve(String viewName) {
        return new RedirectView(extractRedirectUri(viewName));
    }

    private String extractRedirectUri(String viewName) {
        return viewName.substring(DEFAULT_REDIRECT_PREFIX.length()).trim();
    }
}
