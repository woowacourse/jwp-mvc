package nextstep.mvc.tobe.exception;

import java.io.IOException;

public class HttpServletRequestGetBodyException extends RuntimeException {
    public HttpServletRequestGetBodyException(final IOException e) {
        super(e);
    }
}
