package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private Class<?> clazz;
    private Method method;

    public HandlerExecution(Class<?> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(clazz.newInstance(), request, response);
    }
}
