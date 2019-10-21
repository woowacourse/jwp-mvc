package nextstep.mvc.tobe;

public class NotMatchParameterException extends RuntimeException {
    public NotMatchParameterException() {
        super("일치하지 않는 타입 입니다.");
    }
}
