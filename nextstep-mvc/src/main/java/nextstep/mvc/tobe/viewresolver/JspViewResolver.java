package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;

public class JspViewResolver implements ViewResolver {

    private static final String JSP_SUFFIX = ".jsp";
    public static final String REDIRECT_PREFIX = "redirect:";
    private String viewName;

    @Override
    public boolean isSupport(final ModelAndView mav) {
        if (mav.hasNotViewName()) {
            return false;
        }
        this.viewName = mav.getViewName();
        return isJspSuffix() || isRedirectPrefix();
    }

    @Override
    public View resolve() {
        return new JspView(viewName);
    }

    private boolean isRedirectPrefix() {
        return REDIRECT_PREFIX.startsWith(viewName);
    }

    private boolean isJspSuffix() {
        return JSP_SUFFIX.endsWith(viewName);
    }
}
