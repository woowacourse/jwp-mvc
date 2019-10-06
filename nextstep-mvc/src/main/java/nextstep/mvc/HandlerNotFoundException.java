package nextstep.mvc;

public class HandlerNotFoundException extends RuntimeException {
    public HandlerNotFoundException() {
        super("요청에 대한 Handler를 찾을 수 없습니다.");
    }
}
