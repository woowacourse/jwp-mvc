package nextstep.mvc.exception;

public class NotFoundArgumentResolverException extends RuntimeException {
    private static final String MESSAGE = "처리할 수 있는 Argument Resolver가 없습니다.";

    public NotFoundArgumentResolverException() {
        super(MESSAGE);
    }
}