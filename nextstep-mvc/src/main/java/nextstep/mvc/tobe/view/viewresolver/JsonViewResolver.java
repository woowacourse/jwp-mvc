package nextstep.mvc.tobe.view.viewresolver;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.view.ViewType;

public class JsonViewResolver implements ViewResolver {

    @Override
    public boolean support(ViewType viewType) {
        return ViewType.JSON_VIEW == viewType;
    }

    @Override
    public View resolve(String viewName) {
        return new JsonView();
    }
}
