package nextstep.mvc.exception;

import java.lang.reflect.Method;

public class WrongConstructorOfDeclaringClassException extends  RuntimeException {
    private WrongConstructorOfDeclaringClassException(String format) {
        super(format);
    }

    public static WrongConstructorOfDeclaringClassException from(ReflectiveOperationException e, Method method) {
        return new WrongConstructorOfDeclaringClassException(
                String.format("디폴트 생성자가 필요합니다. at %s. err: %s", method.getDeclaringClass(), e));
    }
}
