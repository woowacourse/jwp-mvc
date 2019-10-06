package nextstep.mvc.tobe;

import nextstep.mvc.tobe.exception.InstanceCreationFailedException;
import nextstep.mvc.tobe.view.EmptyView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution implements ServletRequestHandler {
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

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        ModelAndView mv = (ModelAndView) method.invoke(instance, request, response);

        return mv == null ? new ModelAndView(new EmptyView()) : mv;
    }
}
