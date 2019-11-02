package nextstep.mvc.exception;

public class ViewException extends RuntimeException {
    public ViewException(Exception e) {
        super(e);
    }
}
