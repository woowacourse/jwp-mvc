package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean supports(final Object handler);

    ModelAndView handle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler) throws Exception;
}
