package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.EmptyView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    public Object handle(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(instance, request, response);
    }
}
