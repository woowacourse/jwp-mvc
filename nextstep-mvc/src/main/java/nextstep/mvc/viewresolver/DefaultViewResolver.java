package nextstep.mvc.viewresolver;

import nextstep.mvc.modelandview.ModelAndView;
import nextstep.mvc.view.View;

public class DefaultViewResolver implements ViewResolver {

    @Override
    public boolean canHandle(ModelAndView modelAndView) {
        return modelAndView.isViewInstance();
    }

    @Override
    public View resolveView(ModelAndView modelAndView) {
        return (View) modelAndView.getView();
    }
}
