package nextstep.mvc.tobe.exception;

public class InvalidHandlerAdaptException extends RuntimeException {
    private final static String ERROR_MESSAGE = "현재 요청을 변환할 적절한 어뎁터가 없습니다.";

    public InvalidHandlerAdaptException() {
        super(ERROR_MESSAGE);
    }
}
