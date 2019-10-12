package nextstep.mvc.tobe.handler;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.exception.ReturnTypeNotSupportedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    private Object handler;
    private final Method method;

    public HandlerExecution(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response)
            throws InvocationTargetException, IllegalAccessException {
        final Class<?> returnType = method.getReturnType();

        if (returnType.isAssignableFrom(ModelAndView.class)) {
            return (ModelAndView) method.invoke(handler, request, response);
        }
        if (returnType.isAssignableFrom(String.class)) {
            String path = (String) method.invoke(handler, request, response);
            return new ModelAndView(path);
        }
        throw new ReturnTypeNotSupportedException();
    }
}
