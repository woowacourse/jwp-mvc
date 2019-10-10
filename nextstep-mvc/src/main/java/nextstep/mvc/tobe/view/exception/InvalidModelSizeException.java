package nextstep.mvc.tobe.view.exception;

public class InvalidModelSizeException extends RuntimeException {
    private static final String ERROR_MESSAGE = "적절하지 않는 Model 사이즈입니다.";

    public InvalidModelSizeException() {
        super(ERROR_MESSAGE);
    }
}
