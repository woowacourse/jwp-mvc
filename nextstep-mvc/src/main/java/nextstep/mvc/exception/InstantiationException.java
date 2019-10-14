package nextstep.mvc.exception;

public class InstantiationException extends RuntimeException {
    private static final String MESSAGE = "인스턴스화 할 수 없습니다!";

    public InstantiationException() {
        super(MESSAGE);
    }
}