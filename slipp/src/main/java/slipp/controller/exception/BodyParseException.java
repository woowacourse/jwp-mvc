package slipp.controller.exception;

public class BodyParseException extends RuntimeException {
    private static final String MESSAGE = "Body Parse Failed";

    public BodyParseException() {
        super(MESSAGE);
    }
}
