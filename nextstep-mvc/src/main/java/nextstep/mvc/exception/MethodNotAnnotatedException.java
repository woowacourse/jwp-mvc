package nextstep.mvc.exception;

import java.lang.reflect.Method;

public class MethodNotAnnotatedException extends RuntimeException {
    private MethodNotAnnotatedException(String s) {
        super(s);
    }

    public static MethodNotAnnotatedException from(Method method, Class<?> annotationClass) {
        String s = String.format("메소드에 해당 어노테이션이 존재하지 않습니다. method: %s, annotationClass: %s",
                method.toString(), annotationClass.toString());
        return new MethodNotAnnotatedException(s);
    }
}
