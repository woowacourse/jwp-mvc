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

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (method.getReturnType().equals(String.class)) {
            String viewPath = method.invoke(clazz.newInstance(), request, response).toString();
            return new ModelAndView(viewPath);
        }

        return (ModelAndView) method.invoke(clazz.newInstance(), request, response);
    }
}
