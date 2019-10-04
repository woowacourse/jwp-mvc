package nextstep.mvc.tobe;

public class AnnotationScanFailException extends RuntimeException {
    private static final String ANNOTAION_SCAN_FAIL_MESSAGE = "해당 어노테이션을 찾지 못했습니다.";

    public AnnotationScanFailException(Throwable cause) {
        super(ANNOTAION_SCAN_FAIL_MESSAGE, cause);
    }
}
