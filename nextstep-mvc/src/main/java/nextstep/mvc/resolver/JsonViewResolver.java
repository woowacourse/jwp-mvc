package nextstep.mvc.resolver;

import nextstep.mvc.view.JsonView;
import nextstep.mvc.view.View;

public class JsonViewResolver implements ViewResolver {
    @Override
    public boolean supports(String viewName) {
        return viewName.isEmpty();
    }

    @Override
    public View resolveViewName(String viewName) {
        return new JsonView();
    }
}
