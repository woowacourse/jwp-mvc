package nextstep.mvc.tobe.view.exception;

public class JsonRenderingFailException extends RuntimeException {
    private static final String MESSAGE = "Rendering에 실패했습니다.";

    public JsonRenderingFailException() {
        super(MESSAGE);
    }
}
