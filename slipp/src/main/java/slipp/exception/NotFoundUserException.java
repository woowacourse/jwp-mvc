package slipp.exception;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
        super("해당 유저를 찾을 수 없습니다.");
    }
}
