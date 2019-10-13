package nextstep.mvc.tobe.view.exception;

public class InvalidModelSizeException extends RuntimeException {
    private static final String ERROR_MESSAGE = "적절하지 않는 Model 사이즈입니다.";

    public InvalidModelSizeException() {
        this(ERROR_MESSAGE);
    }

    public InvalidModelSizeException(String message) {
        super(message);
    }
}
