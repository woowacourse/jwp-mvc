package slipp.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotFoundUserException extends RuntimeException {
    private static final String MESSAGE = "사용자를 찾을 수 없습니다.";
    private static final Logger logger = LoggerFactory.getLogger(NotFoundUserException.class);

    public NotFoundUserException(String userId) {
        super(MESSAGE);
        logger.debug("Requested User ID: {}", userId);
    }
}
