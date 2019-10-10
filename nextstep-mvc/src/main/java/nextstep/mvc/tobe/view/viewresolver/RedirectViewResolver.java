package nextstep.mvc.tobe.view.viewresolver;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;

public class RedirectViewResolver implements ViewResolver {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean support(ModelAndView mav) {
        return mav.getViewName().startsWith(DEFAULT_REDIRECT_PREFIX);
    }

    @Override
    public View resolve(ModelAndView mav) {
        return new RedirectView(mav.getViewName().substring(DEFAULT_REDIRECT_PREFIX.length()));
    }
}
