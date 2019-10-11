package nextstep.mvc.tobe.argumentresolver.exception;

public class ArgumentResolveFailedException extends RuntimeException {

    public ArgumentResolveFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentResolveFailedException() {
        super();
    }
}
