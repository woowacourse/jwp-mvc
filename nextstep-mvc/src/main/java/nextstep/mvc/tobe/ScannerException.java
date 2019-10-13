package nextstep.mvc.tobe;

public class ScannerException extends RuntimeException {
    public ScannerException(String scanner) {
        super(scanner + " 에서 스캔하는데 실패했습니다");
    }
}
