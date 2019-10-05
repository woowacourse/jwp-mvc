package nextstep.mvc.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerScannerException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScannerException.class);

    public ControllerScannerException() {
        super();
    }

    public ControllerScannerException(final String message) {
        super(message);
        logger.error(message);
    }
}
