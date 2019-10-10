package nextstep.mvc.tobe.view.viewresolver;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;

public class JsonViewResolver implements ViewResolver {
    @Override
    public boolean support(ModelAndView mav) {
        return mav.viewNotExist();
    }

    @Override
    public View resolve(ModelAndView mav) {
        return new JsonView();
    }
}
