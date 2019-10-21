package nextstep.mvc;

public class ViewException extends RuntimeException {
    public ViewException(Exception e) {
        super(e);
    }
}
