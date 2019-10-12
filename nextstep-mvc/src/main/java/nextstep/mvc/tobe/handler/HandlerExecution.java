package nextstep.mvc.tobe.handler;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.exception.ReturnTypeNotSupportedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class HandlerExecution {
    private static final Map<Class<?>, Function<Object, ModelAndView>> strategy = new HashMap<>();

    private final Object handler;
    private final Method method;

    static {
        strategy.put(ModelAndView.class, (object) -> (ModelAndView) object);
        strategy.put(String.class, (object) -> new ModelAndView((String) object));
    }

    public HandlerExecution(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response)
            throws InvocationTargetException, IllegalAccessException {

        final Class<?> returnType = method.getReturnType();
        Object returnValue = method.invoke(handler, request, response);
        return apply(returnType, returnValue);
    }

    private ModelAndView apply(final Class<?> returnType, final Object returnValue) {
        Function<Object, ModelAndView> function = strategy.get(returnType);
        if (function == null) {
            throw new ReturnTypeNotSupportedException();
        }
        return function.apply(returnValue);
    }
}
