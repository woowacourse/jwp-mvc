package nextstep.exception;

public class PageNotFoundException extends Exception {
    public PageNotFoundException() {
        super("페이지를 찾을 수 없습니다.");
    }
}
