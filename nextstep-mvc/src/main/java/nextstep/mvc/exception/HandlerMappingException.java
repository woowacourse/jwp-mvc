package nextstep.mvc.exception;

public class HandlerMappingException extends RuntimeException {

    public HandlerMappingException(Exception e) {
        super(e);
    }
}
