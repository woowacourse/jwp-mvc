package nextstep.mvc.tobe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlerMethodArgumentResolverException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(HandlerMethodArgumentResolverException.class);

    public HandlerMethodArgumentResolverException(final String message) {
        super(message);
        logger.error(message);
    }
}
