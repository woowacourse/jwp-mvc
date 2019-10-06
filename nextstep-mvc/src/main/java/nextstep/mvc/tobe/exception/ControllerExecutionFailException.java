package nextstep.mvc.tobe.exception;

public class ControllerExecutionFailException extends RuntimeException {
    private static final String MESSAGE = "controller 실행에 실패하였습니다.";

    public ControllerExecutionFailException() {
        super(MESSAGE);
    }
}
