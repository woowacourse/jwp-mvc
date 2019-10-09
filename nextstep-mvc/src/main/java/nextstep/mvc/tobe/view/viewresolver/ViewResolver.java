package nextstep.mvc.tobe.view.viewresolver;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;

public interface ViewResolver {
    boolean canHandle(ModelAndView modelAndView);

    View resolveView(ModelAndView modelAndView);
}
