package slipp;

import nextstep.mvc.asis.Controller;

public interface LegacyHandlerMapping { // TODO 이름 좀..
    void initialize();

    Controller getHandler(String requestUri);
}