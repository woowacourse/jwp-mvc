package nextstep.mvc.tobe.exception;

public class NotFoundControllerException extends RuntimeException {
    public NotFoundControllerException() {
        super("Not found controller annotation annotated class");
    }
}
