package nextstep.mvc.exception;

public class NotSupportedBasePackageTypeException extends RuntimeException {
    public NotSupportedBasePackageTypeException(String s) {
        super(s);
    }

    public static NotSupportedBasePackageTypeException basePackage(Object basePackage) {
        String s = String.format("지원하지 않는 BasePackage 타입입니다. basePackage: %s", basePackage.toString());
        return new NotSupportedBasePackageTypeException(s);
    }
}
