package nextstep.mvc.view.viewresolver;

import nextstep.mvc.view.RedirectView;

public class RedirectViewResolver implements ViewResolver {

    @Override
    public boolean canResolve(Object view) {
        return view instanceof RedirectView;
    }
}
