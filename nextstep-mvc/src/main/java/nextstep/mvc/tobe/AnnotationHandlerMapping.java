package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {

    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class<?> clazz : classes) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    String pathInfo = request.getRequestURI();
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    if (annotation.value().equals(pathInfo)) {
                        RequestMethod[] requestMethods = annotation.method();
                        for (RequestMethod requestMethod : requestMethods) {
                            if (requestMethod.toString().equals(request.getMethod().toUpperCase())) {
                                return new HandlerExecution(method);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
