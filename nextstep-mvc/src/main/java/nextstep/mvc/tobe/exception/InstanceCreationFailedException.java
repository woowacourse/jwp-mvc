package nextstep.mvc.tobe.exception;

public class InstanceCreationFailedException extends RuntimeException {
    public InstanceCreationFailedException(final Exception e) {
        super(e);
    }
}
