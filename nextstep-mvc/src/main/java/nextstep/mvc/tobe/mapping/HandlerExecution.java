package nextstep.mvc.tobe.mapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerExecution {
    Object handle(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
