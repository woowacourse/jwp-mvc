package nextstep.mvc.exceptions;

public class HandlerNotFoundException extends RuntimeException {

    private static final String HANDLER_NOT_FOUND_MESSAGE = "Handler Not Found!";

    public HandlerNotFoundException() {
        super(HANDLER_NOT_FOUND_MESSAGE);
    }
}
