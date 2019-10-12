package nextstep.mvc.tobe.exception;

public class FinalFieldSetException extends RuntimeException {
    private static final String MESSAGE = "Final field 는 수정할 수 없습니다.";

    public FinalFieldSetException() {
        super(MESSAGE);
    }
}
