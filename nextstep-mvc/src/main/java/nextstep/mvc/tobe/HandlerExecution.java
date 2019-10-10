package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.JspView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private Object target;
    private Method method;

    public HandlerExecution(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = (String) method.invoke(target, request, response);
        return new ModelAndView(new JspView(url));
    }
}
