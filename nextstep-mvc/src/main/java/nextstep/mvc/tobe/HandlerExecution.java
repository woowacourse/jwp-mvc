package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerExecution {
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
