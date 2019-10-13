package nextstep.mvc.resolver;

import nextstep.mvc.view.JsonView;
import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.View;

public class JsonViewResolver implements ViewResolver {
    @Override
    public boolean supports(ModelAndView mav) {
        return mav.getView() == null;
    }

    public View resolveView(Object view) {
        return new JsonView();
    }
}
