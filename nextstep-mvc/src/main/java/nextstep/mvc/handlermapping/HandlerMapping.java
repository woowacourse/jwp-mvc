package nextstep.mvc.handlermapping;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    boolean canHandle(HttpServletRequest request);

    Object getHandler(HttpServletRequest request);
}
