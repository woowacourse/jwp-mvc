package nextstep.mvc.tobe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstanceCreationFailedException extends RuntimeException {
    private final Logger logger = LoggerFactory.getLogger(InstanceCreationFailedException.class);

    public InstanceCreationFailedException(ReflectiveOperationException e) {
        super(e);
        logger.debug(e.getMessage());
    }
}
