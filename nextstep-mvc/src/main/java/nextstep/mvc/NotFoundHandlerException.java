package nextstep.mvc;

public class NotFoundHandlerException extends RuntimeException {
    private static final String NOT_FOUND_HANDLER_EXCEPTION_MESSAGE = "찾을 수 없는 핸들러입니다.";

    public NotFoundHandlerException() {
        super(NOT_FOUND_HANDLER_EXCEPTION_MESSAGE);
    }
}
