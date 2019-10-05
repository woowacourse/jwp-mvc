package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean supports(Object handler);

    ModelAndView handle(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
