package nextstep.mvc.exception;

public class HandlerKeyUrlNotExistException extends RuntimeException {
    public HandlerKeyUrlNotExistException() {
        super("HandlerKey 는 value 를 포함해야 합니다.");
    }

}
