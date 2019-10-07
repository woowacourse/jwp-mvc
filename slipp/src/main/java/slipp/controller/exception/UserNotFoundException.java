package slipp.controller.exception;

public class UserNotFoundException extends RuntimeException {

    public static final String MESSAGE = "사용자를 찾을 수 없습니다";

    public UserNotFoundException(String requestedUserId) {
        super(MESSAGE + ": " + requestedUserId);
    }
}
