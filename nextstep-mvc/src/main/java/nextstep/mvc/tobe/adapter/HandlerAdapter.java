package nextstep.mvc.tobe.adapter;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    ModelAndView handle(final HttpServletRequest req, final HttpServletResponse resp, final Object handler) throws Exception;

    boolean supports(final Object handler);
}