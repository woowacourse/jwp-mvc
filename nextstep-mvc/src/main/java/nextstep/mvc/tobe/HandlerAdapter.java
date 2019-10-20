package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean supports(Object handler);
    ModelAndView run(Object handler, HttpServletRequest req, HttpServletResponse res) throws Exception;
}