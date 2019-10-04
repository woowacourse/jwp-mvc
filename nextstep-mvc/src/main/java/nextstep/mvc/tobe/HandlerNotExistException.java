package nextstep.mvc.tobe;

public class HandlerNotExistException extends RuntimeException {
    private static final String HANDLER_NOT_EXIST_MESSAGE = "핸들러가 존재하지 않습니다.";

    public HandlerNotExistException() {
        super(HANDLER_NOT_EXIST_MESSAGE);
    }
}
