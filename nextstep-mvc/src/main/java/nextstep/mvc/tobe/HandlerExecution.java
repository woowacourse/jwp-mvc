package nextstep.mvc.tobe;

import nextstep.mvc.asis.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution implements Execution {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);

    private Method method;
    private Object instance;

    public HandlerExecution(Method method) {
        this.method = method;
        try {
            instance = method.getDeclaringClass().newInstance();
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (String) method.invoke(instance, request, response);
    }

    @Override
    public String toString() {
        return "HandlerExecution{" +
                "method=" + method +
                '}';
    }
}
