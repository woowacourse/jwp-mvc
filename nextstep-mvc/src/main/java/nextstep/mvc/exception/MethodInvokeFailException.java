package nextstep.mvc.exception;

public class MethodInvokeFailException extends RuntimeException {
    private MethodInvokeFailException(Exception e) {
        super(e);
    }

    public static MethodInvokeFailException ofException(ReflectiveOperationException e) {
        return new MethodInvokeFailException(e);
    }
}
