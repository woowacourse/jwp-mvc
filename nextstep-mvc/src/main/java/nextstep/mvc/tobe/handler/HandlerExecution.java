package nextstep.mvc.tobe.handler;

import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final Logger logger = LoggerFactory.getLogger(HandlerExecution.class);

    private Object target;
    private Method method;

    public HandlerExecution(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = (String) method.invoke(target, request, response);
        logger.debug("JSP support() viewName : {}", viewName);

        return new ModelAndView(viewName);
    }
}
