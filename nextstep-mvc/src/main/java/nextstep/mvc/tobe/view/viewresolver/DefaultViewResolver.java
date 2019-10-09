package nextstep.mvc.tobe.view.viewresolver;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.view.viewresolver.ViewResolver;

public class DefaultViewResolver implements ViewResolver {

    @Override
    public boolean canHandle(ModelAndView modelAndView) {
        return modelAndView.getView() instanceof View;
    }

    @Override
    public View resolveView(ModelAndView modelAndView) {
        return (View) modelAndView.getView();
    }
}
