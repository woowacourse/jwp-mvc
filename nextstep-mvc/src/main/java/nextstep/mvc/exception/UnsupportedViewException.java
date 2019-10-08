package nextstep.mvc.exception;

public class UnsupportedViewException extends RuntimeException {
    private static final String UNSUPPORTED_VIEW_MESSAGE = "지원하지 않는 뷰입니다.";

    public UnsupportedViewException() {
        super(UNSUPPORTED_VIEW_MESSAGE);
    }
}
