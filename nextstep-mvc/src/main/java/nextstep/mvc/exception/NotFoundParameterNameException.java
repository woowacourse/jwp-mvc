package nextstep.mvc.exception;

public class NotFoundParameterNameException extends RuntimeException {
    private static final String MESSAGE = "해당하는 파라미터 이름을 찾을 수 없습니다.";

    public NotFoundParameterNameException() {
        super(MESSAGE);
    }
}
