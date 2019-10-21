package nextstep.mvc.tobe.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerMapping {
    void initialize();

    boolean support(HttpServletRequest req, HttpServletResponse resp);

    Object getHandler(final HttpServletRequest req);
}
