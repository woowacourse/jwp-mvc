package nextstep.mvc.exception;

public class NotSupportedTypeParserException extends RuntimeException {
    private static final String MESSAGE = "지원하지 않는 원시 타입입니다.";

    public NotSupportedTypeParserException() {
        super(MESSAGE);
    }
}