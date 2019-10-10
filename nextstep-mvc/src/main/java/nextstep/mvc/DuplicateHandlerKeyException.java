package nextstep.mvc;

public class DuplicateHandlerKeyException extends RuntimeException {
    private static final String MESSAGE = "중복된 HandlerKey 입니다.";

    public DuplicateHandlerKeyException() {
        super(MESSAGE);
    }
}
