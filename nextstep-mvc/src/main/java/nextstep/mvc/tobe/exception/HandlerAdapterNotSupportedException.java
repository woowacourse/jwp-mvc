package nextstep.mvc.tobe.exception;

public class HandlerAdapterNotSupportedException extends RuntimeException {
    public HandlerAdapterNotSupportedException() {
        this("not support HandlerAdapter");
    }

    public HandlerAdapterNotSupportedException(final String message) {
        super(message);
    }
}
