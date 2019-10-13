package nextstep.mvc.tobe.viewResolver;

import nextstep.mvc.View;
import nextstep.mvc.ViewResolver;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;

public class RedirectViewResolver implements ViewResolver {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean supports(ModelAndView mv) {
        return mv.getViewName().startsWith(DEFAULT_REDIRECT_PREFIX);
    }

    @Override
    public View resolve(ModelAndView mv) {
        return new RedirectView(mv.getViewName());
    }
}
