package nextstep.mvc.tobe.handler;

import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class RequestMappingHandlerExecution implements HandlerExecution {
    private Method method;
    private Object target;

    public RequestMappingHandlerExecution(Method method, Object target) {
        this.method = method;
        this.target = target;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = (ModelAndView) method.invoke(target, request, response);
        return new ModelAndView(result.getViewName(), result.getModel());
    }

}