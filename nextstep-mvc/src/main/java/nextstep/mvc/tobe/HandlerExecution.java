package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private Method method;
    private Object target;

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object result = method.invoke(target, request, response);
        return new ModelAndView(new JspView(String.valueOf(result)));
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}