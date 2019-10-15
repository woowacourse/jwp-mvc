package nextstep.mvc.view.viewresolver;

import nextstep.mvc.view.JspView;

public class JspViewResolver implements ViewResolver {

    @Override
    public boolean canResolve(Object view) {
        return view instanceof JspView;
    }
}
