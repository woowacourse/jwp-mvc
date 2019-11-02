package nextstep.mvc.exception;

public class NotSupportedHandlerException extends RuntimeException {
    public NotSupportedHandlerException(String format) {
        super(format);
    }

    public static NotSupportedHandlerException ofHandler(Object handler) {
        return new NotSupportedHandlerException(String.format("지원하지 않는 타입입니다. handler: {}", handler));
    }
}
