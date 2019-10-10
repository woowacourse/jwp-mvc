package nextstep.mvc.tobe.handlerresolver;

import nextstep.mvc.asis.Controller;

public interface HandlerMapping {
    void initialize();

    Controller getHandler(String requestUri);
}
