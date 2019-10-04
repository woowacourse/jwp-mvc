package nextstep.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotFoundHandlerException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(NotFoundHandlerException.class);

    public NotFoundHandlerException(String requestURI) {
        logger.debug("Request URI: {}", requestURI);
    }
}
