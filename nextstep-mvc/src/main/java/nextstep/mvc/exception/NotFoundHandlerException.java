package nextstep.mvc.exception;

public class NotFoundHandlerException extends RuntimeException{
    public NotFoundHandlerException() {
        super("Not Found Handler");
    }
}
