package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.View;

public class JspViewResolver implements ViewResolver {
    @Override
    public boolean supports(String viewName) {
        return viewName.endsWith(".jsp");
    }

    @Override
    public View resolve(String viewName) {
        return new JspView(viewName);
    }
}
