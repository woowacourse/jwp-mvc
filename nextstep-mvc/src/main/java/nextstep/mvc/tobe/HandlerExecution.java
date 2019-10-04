package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution implements Handler {
    private final Method method;
    private final Object methodDeclaringClass;

    public HandlerExecution(Method method, Object methodDeclaringClass) {
        this.method = method;
        this.methodDeclaringClass = methodDeclaringClass;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(methodDeclaringClass, request, response);
    }
}
