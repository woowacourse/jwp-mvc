package nextstep.mvc;

public class HandlerAdapterNotFoundException extends RuntimeException {
    public HandlerAdapterNotFoundException() {
        super("Handler에 대한 Adapter를 를 찾을 수 없습니다.");
    }
}
