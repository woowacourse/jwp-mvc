package nextstep.exception;

public class MethodNotAllowException extends Exception {
    public MethodNotAllowException() {
        super("허가되지 않은 접근입니다.");
    }
}
