package nextstep.mvc.tobe.exception;

public class InstanceCreationFailedException extends RuntimeException {
    private static final String MESSAGE = "인스턴스 생성에 실패하였습니다.";

    public InstanceCreationFailedException() {
        super(MESSAGE);
    }
}
