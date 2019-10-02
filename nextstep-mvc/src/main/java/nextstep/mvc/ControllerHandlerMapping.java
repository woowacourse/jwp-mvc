package nextstep.mvc;

import nextstep.mvc.asis.Controller;

public interface ControllerHandlerMapping extends HandlerMapping {
    Controller getHandler(String requestUri);
}
