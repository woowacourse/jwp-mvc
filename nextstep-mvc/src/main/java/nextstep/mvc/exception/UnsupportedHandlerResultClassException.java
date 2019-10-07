package nextstep.mvc.exception;

public class UnsupportedHandlerResultClassException extends RuntimeException {
    private static final String MESSAGE = "HandlerAdapter에서 지원하지 않는 클래스입니다.";

    public UnsupportedHandlerResultClassException() {
        super(MESSAGE);
    }
}
