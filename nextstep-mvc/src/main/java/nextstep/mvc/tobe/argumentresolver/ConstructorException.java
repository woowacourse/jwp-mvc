package nextstep.mvc.tobe.argumentresolver;

public class ConstructorException extends RuntimeException {
    private static final String MESSAGE = "기본 생성자를 이용한 인스턴스화에 실패했습니다.";

    public ConstructorException() {
        super(MESSAGE);
    }
}
