package nextstep.mvc.tobe.handler;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.handler.support.MvcReturnValueStrategy;
import nextstep.mvc.tobe.handler.support.ReturnValueStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final ReturnValueStrategy strategy = new MvcReturnValueStrategy();

    private final Object handler;
    private final Method method;

    public HandlerExecution(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response)
            throws InvocationTargetException, IllegalAccessException {

        Object returnValue = method.invoke(handler, request, response);
        return strategy.apply(returnValue);
    }
}
