package nextstep.mvc.tobe.handlermapping;

import nextstep.mvc.asis.Controller;

public interface ControllerHandlerMapping extends HandlerMapping {
    Controller getHandler(String requestUri);
}
