package nextstep.mvc.tobe;

import nextstep.mvc.tobe.exception.InstanceCreationFailedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    private Object instance;
    private Method method;

    public HandlerExecution(Class clazz, Method method) {
        this.instance = getInstance(clazz);
        this.method = method;
    }

    private Object getInstance(Class clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new InstanceCreationFailedException(e);
        }
    }

    public Method getMethod() {
        return method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        ModelAndView mv = (ModelAndView) method.invoke(instance, request, response);
        if (mv == null) {
            return new ModelAndView(new EmptyView());
        }
        return mv;
    }
}
