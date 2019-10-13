package nextstep.mvc.viewresolver;

import nextstep.mvc.view.JspView;
import nextstep.mvc.modelandview.ModelAndView;
import nextstep.mvc.view.RedirectView;
import nextstep.mvc.view.View;

public class StringViewResolver implements ViewResolver {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean canHandle(ModelAndView modelAndView) {
        return modelAndView.isStringInstance();
    }

    @Override
    public View resolveView(ModelAndView modelAndView) {
        String viewName = modelAndView.getViewName();
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(viewName);
        }
        return new JspView(viewName);
    }
}
