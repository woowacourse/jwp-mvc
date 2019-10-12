package nextstep.mvc.exception;

public class NoSuchControllerClassException extends RuntimeException {
    public NoSuchControllerClassException() {
        super("요청하신 컨트롤러 클래스가 존재하지 않습니다.");
    }

    public NoSuchControllerClassException(String message) {
        super(message);
    }
}
