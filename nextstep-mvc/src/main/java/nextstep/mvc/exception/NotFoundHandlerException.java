package nextstep.mvc.exception;

public class NotFoundHandlerException extends RuntimeException {
    private static final String MESSAGE = "그러한 핸들러는 없습니다!";

    public NotFoundHandlerException() {
        super(MESSAGE);
    }
}
