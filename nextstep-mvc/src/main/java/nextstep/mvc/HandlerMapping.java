package nextstep.mvc;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    boolean support(HttpServletRequest request);

    Handler getHandler(HttpServletRequest request);
}
