package nextstep.mvc.tobe.exception;

public class NotSupportedArgumentResolverException extends RuntimeException {
    private static final String MESSAGE = "불가능한 Argument Resolve 입니다.";

    public NotSupportedArgumentResolverException() {
        super(MESSAGE);
    }
}