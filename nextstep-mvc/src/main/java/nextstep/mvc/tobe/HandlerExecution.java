package nextstep.mvc.tobe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution implements Handler {
    private final Object instance;
    private final Method method;
    private final ArgumentResolver argumentResolver;

    public HandlerExecution(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
        this.argumentResolver = new ArgumentResolver(method);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ViewAdapter.render(invoke(request, response));
    }

    private Object invoke(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException {
        Object[] arguments = argumentResolver.getArguments(request, response);
        return method.invoke(instance, arguments);
    }

    @Override
    public String toString() {
        return "HandlerExecution{" +
            "instance=" + instance +
            ", method=" + method +
            ", argumentResolver=" + argumentResolver +
            '}';
    }
}
