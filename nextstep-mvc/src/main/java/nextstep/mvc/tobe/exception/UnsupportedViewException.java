package nextstep.mvc.tobe.exception;

public class UnsupportedViewException extends RuntimeException {
    private static final String UNSUPPORTED_VIEW_MESSAGE = "지원하지 않는 뷰 타입입니다.";

    public UnsupportedViewException() {
        super(UNSUPPORTED_VIEW_MESSAGE);
    }
}
