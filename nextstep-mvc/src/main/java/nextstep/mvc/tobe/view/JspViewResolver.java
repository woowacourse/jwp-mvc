package nextstep.mvc.tobe.view;

import com.google.common.base.Strings;

public class JspViewResolver implements ViewResolver {
    private static final String SUF_FIX = ".jsp";

    @Override
    public boolean support(String viewName) {
        if (Strings.isNullOrEmpty(viewName)) {
            return false;
        }

        return viewName.endsWith(SUF_FIX);
    }

    @Override
    public View resolve(String viewName) {
        return new JspView(viewName);
    }
}
