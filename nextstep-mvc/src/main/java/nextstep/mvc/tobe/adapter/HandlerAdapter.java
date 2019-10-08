package nextstep.mvc.tobe.adapter;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.WebRequest;

public interface HandlerAdapter {
    ModelAndView handle(final WebRequest webRequest, final Object handler) throws Exception;

    boolean supports(final Object handler);
}