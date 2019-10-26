package slipp.controller.exception;

public class UserCreationFailException extends RuntimeException {
    public UserCreationFailException(Throwable cause) {
        super(cause);
    }
}
