package nextstep.mvc.tobe.exception;

public class RenderFailedException extends RuntimeException {
    public RenderFailedException(final Exception e) {
        super(e);
    }
}
