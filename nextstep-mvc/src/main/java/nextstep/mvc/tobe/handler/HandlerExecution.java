package nextstep.mvc.tobe.handler;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    private Class<?> clazz;
    private Object handler;
    private final Method method;

    public HandlerExecution(Class<?> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public HandlerExecution(Object newInstance, Method method) {
        this.handler = newInstance;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Constructor constructor = clazz.getDeclaredConstructor();
        return (ModelAndView) method.invoke(constructor.newInstance(), request, response);
    }

    public ModelAndView handle1(HttpServletRequest request, HttpServletResponse response)
            throws InvocationTargetException, IllegalAccessException {

        return (ModelAndView) method.invoke(handler, request, response);
    }
}
