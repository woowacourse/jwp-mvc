package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean supports(Object handler);
    ModelAndView handler(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception;
}
