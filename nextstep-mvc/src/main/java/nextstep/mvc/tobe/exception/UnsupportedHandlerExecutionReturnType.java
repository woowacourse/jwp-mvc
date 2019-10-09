package nextstep.mvc.tobe.exception;

public class UnsupportedHandlerExecutionReturnType extends RuntimeException {
    private static final String UNSUPPORTED_HANDLER_EXECUTION_RETURN_TYPE_MESSAGE = "지원하지 않는 컨트롤러 메서드 리턴 타입입니다.";

    public UnsupportedHandlerExecutionReturnType() {
        super(UNSUPPORTED_HANDLER_EXECUTION_RETURN_TYPE_MESSAGE);
    }
}
