package nextstep.mvc.tobe.view.exception;

public class ViewRenderException extends RuntimeException {
    public ViewRenderException() {
        super("Exception occurred during rendering view!");
    }
}
