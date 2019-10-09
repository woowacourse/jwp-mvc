package nextstep.mvc.exception;

public class ControllerInstantiateException extends RuntimeException{
    public ControllerInstantiateException() {
        super("인스턴스를 생성할 수 없습니다.");
    }
}
