package nextstep.mvc.core;

public class FailToComponentScanException extends RuntimeException {
    public FailToComponentScanException() {
    }

    public FailToComponentScanException(String message) {
        super(message);
    }
}
