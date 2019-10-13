package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.View;

public class JsonViewResolver implements ViewResolver {
    @Override
    public boolean supports(String viewName) {
        return viewName == null;
    }

    @Override
    public View resolve(String viewName) {
        return new JsonView();
    }
}
