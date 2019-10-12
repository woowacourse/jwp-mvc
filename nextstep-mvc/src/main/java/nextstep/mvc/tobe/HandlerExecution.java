package nextstep.mvc.tobe;

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
        //todo 변수명 변경
        Object object = method.invoke(target, request, response);

        if (object instanceof String) {
            String url = (String) object;
            return new ModelAndView(new JspView(url));
        }

        return (ModelAndView) object;
    }
}
