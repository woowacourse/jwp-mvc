package nextstep.mvc;

public class UnsupportedHandlerTypeException extends RuntimeException {
    private static final String UNSUPPORTED_HANDLER_TYPE_MESSAGE = "지원하지 않는 핸들러 타입입니다.";

    public UnsupportedHandlerTypeException() {
        super(UNSUPPORTED_HANDLER_TYPE_MESSAGE);
    }
}
