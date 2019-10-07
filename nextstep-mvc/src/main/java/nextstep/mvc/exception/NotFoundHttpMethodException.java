package nextstep.mvc.exception;

public class NotFoundHttpMethodException extends RuntimeException {
    public NotFoundHttpMethodException() {
        super("지원하지 않는 메소드 입니다.");
    }
}
