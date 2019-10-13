package nextstep.mvc;

import nextstep.mvc.tobe.view.ModelAndView;

public interface ViewResolver {
    boolean supports(ModelAndView mv);

    View resolve(ModelAndView mv);
}