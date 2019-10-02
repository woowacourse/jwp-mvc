package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private final Class<?> clazz;
    private final Method method;

    public HandlerExecution(Class<?> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(clazz.getDeclaredConstructor().newInstance(), request, response);
    }

    @Override
    public String toString() {
        return "HandlerExecution{" +
            "clazz=" + clazz +
            ", method=" + method +
            '}';
    }
}
