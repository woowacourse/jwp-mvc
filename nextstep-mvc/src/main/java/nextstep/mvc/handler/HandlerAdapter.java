package nextstep.mvc.handler;

import nextstep.mvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean apply(Object handler);

    ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception;
}
