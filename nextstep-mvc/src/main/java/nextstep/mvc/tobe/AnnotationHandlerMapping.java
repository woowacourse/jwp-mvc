package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() throws IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> clazz = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class<?> claz : clazz) {
            Object object = claz.newInstance();
            Method[] methods = claz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

                    HandlerKey handlerKey = new HandlerKey(requestMapping.value(), requestMapping.method());
                    HandlerExecution handlerExecution = new HandlerExecution(object, method);

                    handlerExecutions.put(handlerKey, handlerExecution);
                }
            }
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
