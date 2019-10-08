package nextstep.mvc.tobe.exception;

public class BadRequestException extends RuntimeException {
    private static final String ERROR_MESSAGE = "적절하지 않은 요청입니다.";

    public BadRequestException() {
        super(ERROR_MESSAGE);
    }
}
