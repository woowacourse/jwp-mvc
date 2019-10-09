package nextstep.mvc.tobe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);

    private Class<?> declaredClass;
    private Method method;

    public HandlerExecution(Class<?> declaredClass, Method method) {
        this.declaredClass = declaredClass;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        log.debug("class:{}", declaredClass);
        log.debug("method:{}", method);
        return (ModelAndView) method.invoke(declaredClass.newInstance(), request, response);
    }
}
