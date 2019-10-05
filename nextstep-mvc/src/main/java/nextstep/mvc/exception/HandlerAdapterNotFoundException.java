package nextstep.mvc.exception;

public class HandlerAdapterNotFoundException extends RuntimeException {
    private static final String MESSAGE = "HandlerAdapter를 찾을 수 없습니다.";

    public HandlerAdapterNotFoundException() {
        super(MESSAGE);
    }
}
