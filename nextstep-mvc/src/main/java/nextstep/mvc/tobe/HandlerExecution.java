package nextstep.mvc.tobe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);
    private Method method;
    private Object declearedObject;

    public HandlerExecution(Method method, Object declearedObject) {
        this.method = method;
        this.declearedObject = declearedObject;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        log.debug("Method : {}", method);
        return (ModelAndView) method.invoke(declearedObject, new Object[]{request, response});
    }
}
