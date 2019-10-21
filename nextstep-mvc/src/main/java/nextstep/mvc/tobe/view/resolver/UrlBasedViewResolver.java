package nextstep.mvc.tobe.view.resolver;

import nextstep.mvc.tobe.View;
import nextstep.mvc.tobe.view.ForwardView;
import nextstep.mvc.tobe.view.RedirectView;

public class UrlBasedViewResolver implements ViewResolver {
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String FORWARD_PREFIX = "forward:";

    @Override
    public View resolveViewName(String name) {
        if (name.startsWith(REDIRECT_PREFIX)) {
            return new RedirectView(name.substring(REDIRECT_PREFIX.length()));
        }
        return new ForwardView(name.substring(FORWARD_PREFIX.length()));
    }

    @Override
    public Boolean canHandle(String viewName) {
        return viewName.startsWith(REDIRECT_PREFIX) || viewName.startsWith(FORWARD_PREFIX);
    }
}
