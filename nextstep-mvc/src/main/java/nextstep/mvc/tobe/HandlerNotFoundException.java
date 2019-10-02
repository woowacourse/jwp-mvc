package nextstep.mvc.tobe;

public class HandlerNotFoundException extends RuntimeException {
    private static final String HANDLER_NOT_FOUND_MESSAGE = "경로에 해당하는 핸들러가 존재하지 않습니다.";

    public HandlerNotFoundException() {
        super(HANDLER_NOT_FOUND_MESSAGE);
    }
}
