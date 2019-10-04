package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution implements Handler {
    private final Method method;
    private final Object object;

    public HandlerExecution(Method method, Object object) {
        this.method = method;
        this.object = object;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(object, request, response);
    }
}
