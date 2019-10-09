package slipp.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnAuthorizedException extends RuntimeException {
    private static final String MESSAGE = "다른 사용자의 정보를 수정할 수 없습니다.";
    private static final Logger logger = LoggerFactory.getLogger(UnAuthorizedException.class);

    public UnAuthorizedException(String userId) {
        super(MESSAGE);
        logger.debug("Requested User ID: {}", userId);
    }
}
