package nextstep.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution implements Controller {
    private Method method;

    public HandlerExecution(Method method) {
        this.method = method;
    }

    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InstancePool instancePool = InstancePool.getInstance();
        Object instance = instancePool.instanceOf(method.getDeclaringClass());
        return method.invoke(instance, request, response);
    }

    @Override
    public String toString() {
        return "HandlerExecution{" +
                "method=" + method +
                '}';
    }
}
