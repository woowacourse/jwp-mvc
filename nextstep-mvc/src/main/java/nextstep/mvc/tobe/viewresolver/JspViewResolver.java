package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;

public class JspViewResolver implements ViewResolver {
    private String viewName;

    @Override
    public boolean supports(ModelAndView mav) {
        if (mav.isViewNameExist()) {
            this.viewName = mav.getViewName();
            return isJspSuffix();
        }

        return false;
    }

    private boolean isJspSuffix() {
        return viewName.endsWith(JspView.JSP_SUFFIX);
    }

    @Override
    public View resolve() {
        return new JspView(viewName);
    }
}
