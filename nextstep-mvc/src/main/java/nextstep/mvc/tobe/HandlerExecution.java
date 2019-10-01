package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private Class<?> clazz;
    private Method method;

    public HandlerExecution(Class<?> aClass, Method method) {
        this.clazz = aClass;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        method.invoke(clazz.newInstance(), request, response);
        return null;
    }
}
