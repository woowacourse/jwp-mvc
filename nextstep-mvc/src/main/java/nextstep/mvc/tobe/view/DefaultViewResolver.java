package nextstep.mvc.tobe.view;

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
