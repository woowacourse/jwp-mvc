package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;

import static nextstep.mvc.tobe.view.RedirectView.DEFAULT_REDIRECT_PREFIX;

public class UrlBasedViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(Object view) {
        if (view instanceof String) {
            if (((String) view).endsWith(".jsp")) {
                return new JspView((String) view);
            }
            if (((String) view).startsWith(DEFAULT_REDIRECT_PREFIX)) {
                return new RedirectView((String) view);
            }
        }
//        if (view instanceof View) {
//            return (View) view;
//        }
        return new JsonView();
    }
}
