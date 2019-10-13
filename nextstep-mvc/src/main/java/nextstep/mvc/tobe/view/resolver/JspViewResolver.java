package nextstep.mvc.tobe.view.resolver;

import nextstep.mvc.tobe.View;
import nextstep.mvc.tobe.view.JspView;

public class JspViewResolver implements ViewResolver {
    private final String JSP_SUFFIX = ".jsp";

    @Override
    public View resolveViewName(String name) {
        return new JspView(name);
    }

    @Override
    public Boolean canHandle(String viewName) {
        return viewName.endsWith(JSP_SUFFIX);
    }
}
