package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private final Method method;
    private final Object instance;

    public HandlerExecution(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return (ModelAndView) method.invoke(instance, req, resp);
    }
}
