package nextstep.exception;

public class NoSuchHandlerException extends RuntimeException {
    public NoSuchHandlerException() {
        super();
    }

    public NoSuchHandlerException(String message) {
        super(message);
    }
}
