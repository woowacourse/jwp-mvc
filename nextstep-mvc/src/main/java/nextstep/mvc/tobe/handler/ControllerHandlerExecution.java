package nextstep.mvc.tobe.handler;

import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class ControllerHandlerExecution implements HandlerExecution {
    private Method method;
    private Object target;

    public ControllerHandlerExecution(Method method, Object target) {
        this.method = method;
        this.target = target;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object result = method.invoke(target, request, response);
        return new ModelAndView(String.valueOf(result));
    }

}
