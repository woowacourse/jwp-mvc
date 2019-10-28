package nextstep.mvc.exception;

import java.lang.reflect.Method;

public class WrongHandlerExecutionParameterTypeException extends RuntimeException {
    private WrongHandlerExecutionParameterTypeException(String format) {
        super(format);
    }

    public static WrongHandlerExecutionParameterTypeException ofMethod(Method method) {
        return new WrongHandlerExecutionParameterTypeException(String.format("HandlerExecution 을 만족하지 않는 메소드 시그니처입니다. (method: %s)", method.toString()));
    }
}
