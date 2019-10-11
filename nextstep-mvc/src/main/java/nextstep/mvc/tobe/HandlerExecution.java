package nextstep.mvc.tobe;

import nextstep.mvc.tobe.argumentresolver.MethodParameters;

import java.lang.reflect.Method;

public class HandlerExecution {
    private final Object instance;
    private final Method method;

    public HandlerExecution(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public Object handle(Object... arguments) throws Exception {
        return method.invoke(instance, arguments);
    }

    public MethodParameters getMethodParameters() {
        return new MethodParameters(method);
    }
}
