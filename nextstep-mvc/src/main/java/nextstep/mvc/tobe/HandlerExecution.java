package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private final Method method;
    private final Class<?> clazz;

    public HandlerExecution(Method method, Class<?> clazz) {
        this.method = method;
        this.clazz = clazz;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(clazz.getDeclaredConstructor().newInstance(), request, response);
    }
}
