package nextstep.mvc.tobe.exception;

import java.io.IOException;

public class JsonViewRenderException extends RuntimeException {
    public JsonViewRenderException(final IOException e) {
        super(e);
    }
}
