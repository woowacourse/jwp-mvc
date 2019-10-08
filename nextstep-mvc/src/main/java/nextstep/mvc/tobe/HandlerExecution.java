package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution implements Execution {
    private Method method;
    private Object instance;

    public HandlerExecution(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return method.invoke(instance, request, response);
    }

    @Override
    public String toString() {
        return "HandlerExecution{" +
                "method=" + method +
                '}';
    }
}
