package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.View;
import org.apache.commons.lang3.StringUtils;

public class JspViewResolver implements ViewResolver {
    private static final String JSP_VIEW_SUFFIX = ".jsp";

    @Override
    public boolean supports(String viewName) {
        return !StringUtils.isEmpty(viewName) &&
                viewName.endsWith(JSP_VIEW_SUFFIX);
    }

    @Override
    public View resolve(String viewName) {
        return new JspView(viewName);
    }
}
