package nextstep.mvc;

import javax.servlet.http.HttpServletRequest;

public class BadHttpRequestException extends RuntimeException {
    private BadHttpRequestException(String s) {
        super(s);
    }

    public static BadHttpRequestException from(HttpServletRequest request) {
        return new BadHttpRequestException(String.format("uri: %s, method: %s", request.getRequestURI(), request.getMethod()));
    }
}
