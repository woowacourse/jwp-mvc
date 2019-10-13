package nextstep.mvc.resolver;

import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.View;

public class DefaultViewResolver implements ViewResolver {
    @Override
    public boolean supports(ModelAndView mav) {
        return mav.getViewName().isEmpty() && mav.getView() instanceof View;
    }

    public View resolveView(Object view) {
        return (View) view;
    }
}
