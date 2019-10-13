package nextstep.mvc.tobe.exception;

public class ControllerCreateException extends RuntimeException {
    private static final String ERROR_MESSAGE = "컨트롤러 생성이 불가능합니다.";

    public ControllerCreateException() {
        super(ERROR_MESSAGE);
    }
}
