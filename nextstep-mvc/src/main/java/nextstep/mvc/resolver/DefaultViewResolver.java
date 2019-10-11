package nextstep.mvc.resolver;

import nextstep.mvc.view.View;

public class DefaultViewResolver implements ViewResolver {
    @Override
    public boolean supports(Object view) {
        return view instanceof View;
    }

    public View resolveView(Object view) {
        return (View) view;
    }
}
