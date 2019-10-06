package nextstep.mvc;

public class ViewNotFoundException extends RuntimeException {
    public ViewNotFoundException() {
        super("View를 찾을 수 없습니다.");
    }
}
