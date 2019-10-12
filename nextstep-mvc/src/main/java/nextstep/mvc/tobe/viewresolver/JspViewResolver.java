package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.View;

public class JspViewResolver implements ViewResolver {
    private static final String JSP_SUFFIX = ".jsp";

    @Override
    public View resolve(String viewName) {
        if (viewName.endsWith(JSP_SUFFIX)) {
            return new JspView(viewName);
        }
        return null;
    }
}
