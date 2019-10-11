package nextstep.mvc.tobe.handler;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    Object getHandler(HttpServletRequest httpServletRequest);
}
