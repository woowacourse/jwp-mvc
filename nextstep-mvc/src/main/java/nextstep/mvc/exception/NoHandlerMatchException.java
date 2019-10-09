package nextstep.mvc.exception;

import javax.servlet.http.HttpServletRequest;

public class NoHandlerMatchException extends RuntimeException {

    public NoHandlerMatchException(HttpServletRequest req) {
        super("No handler matches given request found: " + req.getRequestURI());
    }
}
