package nextstep.mvc.tobe;

public class ViewResolver {
    public View resolve(ModelAndView mav) {
        Object view = mav.getView();

        if (view instanceof String) {
            return new JSPView((String) view);
        }

        if (view instanceof View) {
            return (View) view;
        }

        if (view != null) {
            mav.addObject("model", view);
            return new JsonView();
        }
        return new EmptyView();
    }
}
