package nextstep.mvc.exception;

import nextstep.mvc.HttpStatus;

public class HttpException extends RuntimeException {
    private String code;

    public HttpException(HttpStatus httpStatus) {
        super(httpStatus.getCode() + " " + httpStatus.getMessage());
        this.code = httpStatus.getCode();
    }

    public String getCode() {
        return code;
    }
}
