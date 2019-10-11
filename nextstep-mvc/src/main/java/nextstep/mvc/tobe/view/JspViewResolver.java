package nextstep.mvc.tobe.view;

import com.google.common.base.Strings;

public class JspViewResolver implements ViewResolver {

    @Override
    public boolean support(String viewName) {
        return !Strings.isNullOrEmpty(viewName);
    }

    @Override
    public View resolve(String viewName) {
        return new JspView(viewName);
    }
}
