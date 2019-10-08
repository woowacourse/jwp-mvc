package nextstep.mvc;

import nextstep.mvc.asis.Controller;

public interface HandlerMapping {
    void initialize();

    Object getHandler(String requestUri);
}
