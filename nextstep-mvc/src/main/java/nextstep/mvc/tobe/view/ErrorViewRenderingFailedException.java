package nextstep.mvc.tobe.view;

public class ErrorViewRenderingFailedException extends RuntimeException {
    public ErrorViewRenderingFailedException() {
        super("Error View Rendering에 실패했습니다.");
    }
}
