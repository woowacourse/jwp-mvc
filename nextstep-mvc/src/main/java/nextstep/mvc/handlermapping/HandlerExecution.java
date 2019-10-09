package nextstep.mvc.handlermapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerExecution {
    Object handle(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
