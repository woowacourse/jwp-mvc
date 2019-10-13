package nextstep.mvc.tobe.exception;

public class NotSupportedReturnValueException extends RuntimeException {
    private static final String MESSAGE = "지원되지 않는 Return type 입니다.";

    public NotSupportedReturnValueException() {
        super(MESSAGE);
    }
}
