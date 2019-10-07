package nextstep.mvc.tobe;

import nextstep.mvc.asis.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution implements Controller {
    private Method method;

    public HandlerExecution(Method method) {
        this.method = method;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InstancePool instancePool = InstancePool.getInstance();
        Object instance = instancePool.instanceOf(method.getDeclaringClass());
        return (String) method.invoke(instance, request, response);
    }

    @Override
    public String toString() {
        return "HandlerExecution{" +
                "method=" + method +
                '}';
    }
}
