package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution implements Handler {
    private final Object instance;
    private final Method method;

    public HandlerExecution(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ViewAdapter.render(method.invoke(instance, request, response));
    }

    @Override
    public String toString() {
        return "HandlerExecution{" +
            "instance=" + instance +
            ", method=" + method +
            '}';
    }
}
