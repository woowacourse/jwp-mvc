package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerExecution {
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
