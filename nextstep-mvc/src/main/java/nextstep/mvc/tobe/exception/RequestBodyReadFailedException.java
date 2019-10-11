package nextstep.mvc.tobe.exception;

import java.io.IOException;

public class RequestBodyReadFailedException extends RuntimeException {
    public RequestBodyReadFailedException(final IOException e) {
        super(e);
    }
}
