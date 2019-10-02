package nextstep.mvc.tobe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);
    private Method method;
    private Class clazz;

    public HandlerExecution(Method method, Class clazz) {
        this.method = method;
        this.clazz = clazz;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.debug("Method : {}", method);
        return (ModelAndView) method.invoke(clazz.newInstance(), new Object[]{request, response});
    }
}
