package nextstep.mvc.tobe.exception;

public class NotFoundViewException extends RuntimeException {
    private static final String MESSAGE = "적절한 뷰를 찾을 수 없습니다.";

    public NotFoundViewException() {
        super(MESSAGE);
    }
}
