package nextstep.mvc.viewresolver;

import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.View;

public interface ViewResolver {
    boolean canHandle(ModelAndView modelAndView);

    View resolveView(ModelAndView modelAndView);
}
