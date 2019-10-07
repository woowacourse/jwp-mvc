package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private final Method method;
    private final Object handler;

    public HandlerExecution(Method method, Object handler) {
        this.method = method;
        this.handler = handler;
    }

    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(handler, request, response);
    }
}
