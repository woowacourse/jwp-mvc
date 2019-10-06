package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    ModelAndView handle(final HttpServletRequest req, final HttpServletResponse resp, final Object handler) throws Exception;

    boolean supports(final Object handler);
}