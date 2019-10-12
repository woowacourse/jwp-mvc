package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Adapter {
    boolean isSupport(Object handler);

    ModelAndView handle(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
