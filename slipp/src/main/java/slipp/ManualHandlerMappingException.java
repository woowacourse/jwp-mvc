package slipp;

import javax.servlet.http.HttpServletRequest;

public class ManualHandlerMappingException extends RuntimeException {

    public ManualHandlerMappingException(HttpServletRequest req, Exception e) {
        super("Request URI: " + req.getRequestURI(), e);
    }
}
