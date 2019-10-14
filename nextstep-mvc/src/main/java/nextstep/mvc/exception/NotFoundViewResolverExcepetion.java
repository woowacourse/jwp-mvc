package nextstep.mvc.exception;

public class NotFoundViewResolverExcepetion extends RuntimeException {
    private static final String MESSAGE = "그러한 View Resolver는 없습니다!";

    public NotFoundViewResolverExcepetion() {
        super(MESSAGE);
    }
}
