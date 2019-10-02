package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class AnnotationHandlerMapping {
    private Object[] basePackages;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackages) {
        this.basePackages = basePackages;
    }

    public void initialize() {
        checkBasePackages();
    }

    private void checkBasePackages() {
        for (Object basePackage : basePackages) {
            Reflections reflections = new Reflections(basePackage);
            checkClazz(reflections);
        }
    }

    private void checkClazz(Reflections reflections) {
        for (Class<?> clazz : reflections.getTypesAnnotatedWith(Controller.class)) {
            checkMethods(clazz);
        }
    }

    private void checkMethods(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            putHandlerExecution(method);
        }
    }

    private void putHandlerExecution(Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            handlerExecutions.put(new HandlerKey(requestMapping.value(), requestMapping.method()), new HandlerExecution(method));
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.of(request.getMethod())));
    }
}
