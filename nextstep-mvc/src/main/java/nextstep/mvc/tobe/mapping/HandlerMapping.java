package nextstep.mvc.tobe.mapping;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    Object getHandler(HttpServletRequest request);
}
