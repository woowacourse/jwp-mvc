package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {

    private Object instance;
    private Method method;

    public HandlerExecution(final Object instance, final Method method) {
        this.instance = instance;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(instance, request, response);
    }

    public boolean isSameMethod(final Method method) {
        return this.method.equals(method);
    }
}
