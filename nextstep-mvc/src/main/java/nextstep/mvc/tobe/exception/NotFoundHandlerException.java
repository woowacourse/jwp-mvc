package nextstep.mvc.tobe.exception;

public class NotFoundHandlerException extends RuntimeException {
    public static final String MESSAGE = "핸들러를 찾을 수 없습니다.";

    public NotFoundHandlerException() {
        super(MESSAGE);
    }
}
