package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution implements HandlerResolver {
    private final Method method;
    private final Class<?> clazz;

    public HandlerExecution(Method method, Class<?> clazz) {
        this.method = method;
        this.clazz = clazz;
    }

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return method.invoke(clazz.getDeclaredConstructor().newInstance(), req, resp);
    }
}
