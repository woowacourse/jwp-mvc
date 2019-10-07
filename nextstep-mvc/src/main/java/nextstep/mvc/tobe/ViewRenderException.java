package nextstep.mvc.tobe;

public class ViewRenderException extends RuntimeException {
    private static final String VIEW_RENDER_EXCEPTION_MESSAGE = "뷰 랜더링 중 오류가 발생하였습니다.";

    public ViewRenderException() {
        super(VIEW_RENDER_EXCEPTION_MESSAGE);
    }
}
