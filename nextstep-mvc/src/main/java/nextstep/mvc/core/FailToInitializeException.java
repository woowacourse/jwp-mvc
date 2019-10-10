package nextstep.mvc.core;

public class FailToInitializeException extends RuntimeException {
    public FailToInitializeException() {
    }

    public FailToInitializeException(String message) {
        super(message);
    }
}
