package nextstep.mvc.exception;

public class NotFoundAdapterException extends RuntimeException {
    public NotFoundAdapterException() {
        super("알맞은 어뎁터가 아닙니다.");
    }
}
