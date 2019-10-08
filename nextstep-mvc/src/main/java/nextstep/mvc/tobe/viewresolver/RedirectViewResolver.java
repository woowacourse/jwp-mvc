package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;

public class RedirectViewResolver implements ViewResolver {
    private String viewName;

    @Override
    public boolean supports(final ModelAndView mav) {
        if (mav.isViewNameExist()) {
            this.viewName = mav.getViewName();
            return isRedirectView();
        }

        return false;
    }

    private boolean isRedirectView() {
        return viewName.startsWith(RedirectView.DEFAULT_REDIRECT_PREFIX);
    }

    @Override
    public View resolve() {
        return new RedirectView(viewName);
    }
}
