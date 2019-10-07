package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private final Object instance;
    private final Method method;

    public HandlerExecution(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public Object handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return method.invoke(instance, request, response);
    }

    @Override
    public String toString() {
        return "HandlerExecution{" +
            "instance=" + instance +
            ", method=" + method +
            '}';
    }
}
