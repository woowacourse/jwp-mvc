package nextstep.mvc.exception;

public class KeyNotFoundException extends RuntimeException{
    public KeyNotFoundException() {
        super("키를 찾을 수 없습니다.");
    }
}
