package nextstep.mvc.tobe.core;

public class FailToInitializeException extends RuntimeException {
    public FailToInitializeException() {
    }

    public FailToInitializeException(String message) {
        super(message);
    }
}
