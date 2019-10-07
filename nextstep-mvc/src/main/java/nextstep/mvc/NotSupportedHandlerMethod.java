package nextstep.mvc;

public class NotSupportedHandlerMethod extends RuntimeException {
    public NotSupportedHandlerMethod() {
        super("지원되지 않는 handler 입니다.");
    }
}
