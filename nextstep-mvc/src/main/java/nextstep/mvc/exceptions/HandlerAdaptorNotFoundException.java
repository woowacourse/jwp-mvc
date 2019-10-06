package nextstep.mvc.exceptions;

public class HandlerAdaptorNotFoundException extends RuntimeException {

    private static final String HANDLER_ADAPTOR_NOT_FOUND_MESSAGE = "Handler Adaptor Not Found!";

    public HandlerAdaptorNotFoundException() {
        super(HANDLER_ADAPTOR_NOT_FOUND_MESSAGE);
    }
}
