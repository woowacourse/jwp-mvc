package nextstep.mvc.tobe;

import nextstep.mvc.asis.Execution;

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
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (String) method.invoke(instance, request, response);
    }

    @Override
    public String toString() {
        return "HandlerExecution{" +
                "method=" + method +
                '}';
    }
}
