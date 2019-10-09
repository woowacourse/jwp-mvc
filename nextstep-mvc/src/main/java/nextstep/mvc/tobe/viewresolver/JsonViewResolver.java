package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;

public class JsonViewResolver implements ViewResolver {

    @Override
    public boolean isSupport(final ModelAndView mav) {
        return hasNotViewName(mav);
    }

    private boolean hasNotViewName(final ModelAndView mav) {
        return !mav.hasViewName();
    }

    @Override
    public View resolve() {
        return new JsonView();
    }
}
