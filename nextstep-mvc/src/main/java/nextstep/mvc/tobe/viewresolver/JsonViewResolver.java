package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.View;

public class JsonViewResolver implements ViewResolver {
    @Override
    public boolean supports(final Object view) {
        return view instanceof JsonView;
    }

    @Override
    public View resolveView(final Object view) {
        return (JsonView) view;
    }
}
