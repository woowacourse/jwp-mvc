package nextstep.mvc.tobe;

import java.lang.reflect.Method;

public class HandlerExecution2 {
    private Method method;
    private Object instance;

    public HandlerExecution2(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    Object handle(Object... arguments) throws Exception {
        return method.invoke(instance, arguments);
    }
}
