package nextstep.mvc.resolver;

import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.View;

public interface ViewResolver {
    boolean supports(ModelAndView mav);

    View resolveView(Object view);
}
