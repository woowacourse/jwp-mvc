package nextstep.mvc.tobe.handler;

public class NoSuchHandlerException extends RuntimeException {
    public NoSuchHandlerException() {
        super("No such handler exception occurred!");
    }
}
