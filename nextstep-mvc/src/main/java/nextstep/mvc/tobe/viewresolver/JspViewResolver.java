package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.View;

public class JspViewResolver implements ViewResolver {
    private static final String SUFFIX = ".jsp";

    @Override
    public boolean supports(String viewName) {
        return viewName.endsWith(SUFFIX);
    }

    @Override
    public View resolve(String viewName) {
        return new JspView(viewName);
    }
}
