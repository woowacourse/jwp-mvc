package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.View;

public class JsonViewResolver implements ViewResolver {
    private static final String JSON_VIEW_NAME = "jsonView";

    @Override
    public boolean supports(String viewName) {
        return JSON_VIEW_NAME.equals(viewName);
    }

    @Override
    public View resolve(String viewName) {
        return new JsonView();
    }
}
