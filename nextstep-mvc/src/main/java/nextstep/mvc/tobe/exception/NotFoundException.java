package nextstep.mvc.tobe.exception;

public class NotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "해당 경로에 맞는 페이지가 없습니다.";

    public NotFoundException() {
        super(ERROR_MESSAGE);
    }
}
