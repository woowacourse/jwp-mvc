package slipp.controller.exception;

public class MessageParseException extends RuntimeException {

    public MessageParseException(Exception e) {
        super("Error while parsing request message", e);
    }
}
