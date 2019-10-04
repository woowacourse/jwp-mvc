package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class HandlerExecution {
    private final Method method;

    HandlerExecution(final Method method) {
        this.method = method;
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Constructor constructor = this.method.getDeclaringClass().getDeclaredConstructor();
        return (ModelAndView) method.invoke(constructor.newInstance(), request, response);
    }
}
