package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface HandlerExecution extends Handler {
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception;
}