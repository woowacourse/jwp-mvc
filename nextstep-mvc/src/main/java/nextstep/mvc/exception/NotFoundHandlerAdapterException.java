package nextstep.mvc.exception;

public class NotFoundHandlerAdapterException extends RuntimeException {
    private static final String MESSAGE = "그러한 핸들러 어댑터는 없습니다!";

    public NotFoundHandlerAdapterException() {
        super(MESSAGE);
    }
}
