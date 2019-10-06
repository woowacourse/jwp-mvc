package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean isHandle(Object handler);

    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler);
}
