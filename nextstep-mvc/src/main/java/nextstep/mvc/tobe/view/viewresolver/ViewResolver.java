package nextstep.mvc.tobe.view.viewresolver;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;

public interface ViewResolver {
    boolean support(ModelAndView mav);

    View resolve(ModelAndView mav);
}
