package nextstep.mvc.tobe.handler;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

        controllers.forEach(this::fillHandlerExecutions);
    }

    private void fillHandlerExecutions(Class clazz) {
        Object instance = getInstance(clazz);
        Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> fillHandlerExecution(method, instance));
    }

    private Object getInstance(Class clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("%s not found Constructor", clazz.getName()));
        }
    }

    private void fillHandlerExecution(Method method, Object instance) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        handlerExecutions.put(new HandlerKey(requestMapping.value(), requestMapping.method()), new HandlerExecution(method, instance));
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest req) {
        return handlerExecutions.get(createHandlerKey(req));
    }

    private HandlerKey createHandlerKey(HttpServletRequest request) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        return new HandlerKey(url, RequestMethod.valueOf(method));
    }
}
