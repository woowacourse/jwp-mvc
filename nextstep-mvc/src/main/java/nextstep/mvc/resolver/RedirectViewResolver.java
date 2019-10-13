package nextstep.mvc.resolver;

import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.RedirectView;
import nextstep.mvc.view.View;

public class RedirectViewResolver implements ViewResolver {
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean supports(ModelAndView mav) {
        return mav.getViewName().startsWith(REDIRECT_PREFIX);
    }

    @Override
    public View resolveView(Object view) {
        return new RedirectView((String) view);
    }
}
