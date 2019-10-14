package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;

public interface ViewResolver {

    boolean isSupport(ModelAndView mav);

    View resolve();
}
