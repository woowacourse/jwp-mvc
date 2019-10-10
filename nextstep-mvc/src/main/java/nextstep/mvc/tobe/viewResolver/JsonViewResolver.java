package nextstep.mvc.tobe.viewResolver;

import nextstep.mvc.View;
import nextstep.mvc.ViewResolver;
import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.ModelAndView;

public class JsonViewResolver implements ViewResolver {

    @Override
    public boolean supports(ModelAndView mv) {
        return mv.isJsonView();
    }

    @Override
    public View resolve(ModelAndView mv) {
        return new JsonView();
    }
}
