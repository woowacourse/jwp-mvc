package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.View;

public class JsonViewResolver implements ViewResolver {
    @Override
    public View resolve(String viewName) {
        if (viewName.equals("jsonView")) {
            return new JsonView();
        }

        return null;
    }
}
