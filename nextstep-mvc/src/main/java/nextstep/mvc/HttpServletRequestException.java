package nextstep.mvc;

import nextstep.web.support.HttpStatus;

public class HttpServletRequestException extends RuntimeException {
    private final HttpStatus httpStatus;

    public HttpServletRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
