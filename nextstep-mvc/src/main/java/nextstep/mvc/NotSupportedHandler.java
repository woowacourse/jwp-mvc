package nextstep.mvc;

public class NotSupportedHandler extends RuntimeException {
    public NotSupportedHandler(String format) {
        super(format);
    }

    public static NotSupportedHandler ofHandler(Object handler) {
        return new NotSupportedHandler(String.format("지원하지 않는 타입입니다. handler: {}", handler));
    }
}
