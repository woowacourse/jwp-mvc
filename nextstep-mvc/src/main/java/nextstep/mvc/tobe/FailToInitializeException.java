package nextstep.mvc.tobe;

public class FailToInitializeException extends RuntimeException {
    public FailToInitializeException() {
    }

    public FailToInitializeException(String message) {
        super(message);
    }
}
