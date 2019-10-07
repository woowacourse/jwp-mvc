package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerResolver {
    Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
