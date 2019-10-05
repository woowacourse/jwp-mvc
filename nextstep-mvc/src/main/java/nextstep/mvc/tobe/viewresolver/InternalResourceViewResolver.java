package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.View;

public class InternalResourceViewResolver implements ViewResolver {
    private String urlPrefix;
    private String urlSuffix;

    @Override
    public View resolve(String viewName) {
        return null;
    }
}
