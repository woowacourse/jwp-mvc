package nextstep.mvc.tobe.view.viewresolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.view.ViewType;

public class JspViewResolver implements ViewResolver {

    @Override
    public boolean support(ViewType viewType) {
        return ViewType.JSP_VIEW == viewType;
    }

    @Override
    public View resolve(String viewName) {
        return new JspView(viewName);
    }
}
