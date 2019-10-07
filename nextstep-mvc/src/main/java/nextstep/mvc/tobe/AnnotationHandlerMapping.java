package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.utils.ClassUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        List<Object> handlers = reflections.getTypesAnnotatedWith(Controller.class).stream()
                .map(ClassUtils::createInstance)
                .collect(Collectors.toList());

        handlers.forEach(this::registerHandlerExecution);
    }

    private void registerHandlerExecution(Object handler) {
        Class clazz = handler.getClass();
        List<Method> methods = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .collect(Collectors.toList());
        for (Method method : methods) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            Arrays.stream(annotation.method())
                    .forEach(requestMethod -> {
                        HandlerKey handlerKey = new HandlerKey(annotation.value(), requestMethod);
                        HandlerExecution handlerExecution = new HandlerExecution(handler, method);
                        handlerExecutions.put(handlerKey, handlerExecution);
                    });
        }
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }
}
