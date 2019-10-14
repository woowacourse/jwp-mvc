package nextstep.mvc.view.viewresolver;

import nextstep.mvc.view.JsonView;

public class JsonViewResolver implements ViewResolver {

    @Override
    public boolean canResolve(Object view) {
        return view instanceof JsonView;
    }
}
