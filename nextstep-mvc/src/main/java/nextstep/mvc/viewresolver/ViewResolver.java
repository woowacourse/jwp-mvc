package nextstep.mvc.viewresolver;

import nextstep.mvc.modelandview.ModelAndView;
import nextstep.mvc.view.View;

public interface ViewResolver {
    boolean canHandle(ModelAndView modelAndView);

    View resolveView(ModelAndView modelAndView);
}
