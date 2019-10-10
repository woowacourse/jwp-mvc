package nextstep.mvc.tobe.view.resolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.View;

public class JspViewResolver implements ViewResolver {
    private static final String JSP = ".jsp";

    @Override
    public View resolveViewName(String viewName) {
        if (viewName.endsWith(JSP)) {
            return new JspView(viewName);
        }
        return null;
    }
}
