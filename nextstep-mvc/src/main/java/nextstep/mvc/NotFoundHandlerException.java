package nextstep.mvc;

public class NotFoundHandlerException extends RuntimeException {
    public NotFoundHandlerException() {
        super();
    }

    public NotFoundHandlerException(String message) {
        super(message);
    }
}
