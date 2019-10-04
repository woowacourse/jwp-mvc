package nextstep.mvc.tobe;

import nextstep.mvc.asis.Execution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution implements Execution {
    private Method method;

    public HandlerExecution(Method method) {
        this.method = method;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (String) method.invoke(method.getDeclaringClass().newInstance(), request, response);
    }

    @Override
    public String toString() {
        return "HandlerExecution{" +
                "method=" + method +
                '}';
    }
}
