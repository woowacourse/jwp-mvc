package nextstep.mvc.tobe.exception;

public class AdapterNotExistException extends RuntimeException {

    public static final String ADAPTER_NOT_EXIST_MESSAGE = "어댑터를 찾지 못했습니다.";

    public AdapterNotExistException() {
        super(ADAPTER_NOT_EXIST_MESSAGE);
    }
}
