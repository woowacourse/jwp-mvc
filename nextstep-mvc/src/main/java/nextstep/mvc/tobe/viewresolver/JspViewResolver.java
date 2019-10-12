package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.View;

public class JspViewResolver implements ViewResolver {
    private static final String JSP_SUFFIX = ".jsp";

    @Override
    public boolean supports(String viewName) {
        return viewName != null && viewName.endsWith(JSP_SUFFIX);
    }

    @Override
    public View resolve(String viewName) {
        return new JspView(viewName);
    }
}
