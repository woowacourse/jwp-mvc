package nextstep.mvc.view.viewresolver;

import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.View;

public interface ViewResolver {

    boolean canResolve(Object view);

    default ModelAndView resolve(Object view) {
        return new ModelAndView((View) view);
    }
}
