package nextstep.mvc.tobe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    private Method method;
    private Object instance;

    public HandlerExecution(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    public Method getMethod() {
        return method;
    }

    public Object handle(Object... arguments) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(instance, arguments);
    }
}
