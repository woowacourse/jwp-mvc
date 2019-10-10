package nextstep.mvc.tobe;

import nextstep.mvc.tobe.exception.UnsupportedViewException;

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
            return new JsonView();
        }
        throw new UnsupportedViewException();
    }
}
