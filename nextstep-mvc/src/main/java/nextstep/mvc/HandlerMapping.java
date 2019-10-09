package nextstep.mvc;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    Object getHandler(final HttpServletRequest request);
}
