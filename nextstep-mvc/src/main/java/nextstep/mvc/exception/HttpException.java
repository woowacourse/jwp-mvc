package nextstep.mvc.exception;

import nextstep.mvc.HttpStatus;

public class HttpException extends RuntimeException {
    public HttpException(HttpStatus httpStatus) {
        super(httpStatus.getCode() + " " + httpStatus.getMessage());
    }
}
