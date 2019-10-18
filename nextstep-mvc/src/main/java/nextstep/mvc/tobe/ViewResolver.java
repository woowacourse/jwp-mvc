package nextstep.mvc.tobe;

import nextstep.mvc.exception.UnsupportedViewException;

public class ViewResolver {
    public View resolve(Object view) {

        if (view instanceof String) {
            return new JSPView((String) view);
        }

        if (view instanceof JSPView) {
            return (JSPView) view;
        }

        if (view instanceof JsonView) {
            return (JsonView) view;
        }

        throw new UnsupportedViewException();
    }
}
