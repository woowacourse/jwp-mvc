package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;

public class RedirectViewResolver implements ViewResolver {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean supports(ModelAndView mav) {
        return mav.getViewName().startsWith(DEFAULT_REDIRECT_PREFIX);

    }

    @Override
    public View resolveViewName(String viewName) {
        return new RedirectView(viewName);
    }
}
