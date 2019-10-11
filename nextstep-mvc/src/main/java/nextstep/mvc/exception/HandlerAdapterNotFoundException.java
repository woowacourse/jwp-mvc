package nextstep.mvc.exception;

public class HandlerAdapterNotFoundException extends RuntimeException {
    private static final String HANDLER_ADAPTER_NOT_FOUND_MESSAGE = "어댑터를 찾을 수 없습니다.";

    public HandlerAdapterNotFoundException() {
        super(HANDLER_ADAPTER_NOT_FOUND_MESSAGE);
    }
}
