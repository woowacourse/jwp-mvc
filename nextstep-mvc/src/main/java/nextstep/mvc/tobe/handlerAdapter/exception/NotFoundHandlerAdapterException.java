package nextstep.mvc.tobe.handlerAdapter.exception;

public class NotFoundHandlerAdapterException extends RuntimeException {
    public NotFoundHandlerAdapterException() {
        super("HandlerAdapter를 찾을 수 없습니다.");
    }
}
