package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerExecution {
    ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
