package nextstep.mvc.view.viewresolver;

import nextstep.mvc.view.ModelAndView;

public class ModelAndViewResolver implements ViewResolver {
    @Override
    public boolean canResolve(Object view) {
        return view instanceof ModelAndView;
    }

    @Override
    public ModelAndView resolve(Object view) {
        return (ModelAndView) view;
    }
}
