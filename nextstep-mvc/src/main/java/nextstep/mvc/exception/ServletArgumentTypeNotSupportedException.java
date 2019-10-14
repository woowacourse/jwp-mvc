package nextstep.mvc.exception;

public class ServletArgumentTypeNotSupportedException extends RuntimeException {
    private static final String MESSAGE = "지원하지 않는 서블릿 Argument 타입입니다.";

    public ServletArgumentTypeNotSupportedException() {
        super(MESSAGE);
    }
}
