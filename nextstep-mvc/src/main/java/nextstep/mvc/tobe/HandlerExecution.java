package nextstep.mvc.tobe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final Logger logger = LoggerFactory.getLogger(HandlerExecution.class);

    private final Method method;

    public HandlerExecution(Method method) {
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            return (ModelAndView) method.invoke(method.getDeclaringClass().getConstructor().newInstance(), request, response);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
