package nextstep.mvc.resolver;

import nextstep.mvc.view.JspView;
import nextstep.mvc.view.View;

public class JspViewResolver implements ViewResolver {
    private static final String JSP_SUFFIX = ".jsp";

    @Override
    public boolean supports(Object view) {
        return view != null && ((String) view).endsWith(JSP_SUFFIX);
    }

    @Override
    public View resolveView(Object view) {
        return new JspView((String) view);
    }
}
