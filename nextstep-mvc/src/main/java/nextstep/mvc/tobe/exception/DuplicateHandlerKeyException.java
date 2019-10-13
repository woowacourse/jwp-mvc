package nextstep.mvc.tobe.exception;

public class DuplicateHandlerKeyException extends RuntimeException {
    private static final String ERROR_MESSAGE = "중복된 HandlerKey 값이 존재합니다.";

    public DuplicateHandlerKeyException() {
        super(ERROR_MESSAGE);
    }
}
