package nextstep.mvc.tobe.returnvaluehandler;

import nextstep.mvc.tobe.HandlerMethod;
import nextstep.mvc.tobe.RequestContext;

public interface HandlerMethodReturnValueHandler {
    boolean supports(HandlerMethod handlerMethod);

    void handle(RequestContext requestContext, Object returnValue);
}
