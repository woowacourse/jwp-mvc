package nextstep.mvc.tobe.handler.support;

import nextstep.mvc.tobe.ModelAndView;

public interface ReturnValueStrategy {
    ModelAndView apply(Object returnValue);
}
