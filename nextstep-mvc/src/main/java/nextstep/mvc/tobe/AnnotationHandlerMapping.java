package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.utils.ClassUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import nextstep.web.annotation.RestController;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
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
        List<Object> handlers = Arrays.asList(getHandlersByAnnotation(Controller.class), getHandlersByAnnotation(RestController.class));

        handlers.forEach(this::registerHandlerExecutions);
    }

    private List<Object> getHandlersByAnnotation(Class<? extends Annotation> clazz) {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(clazz).stream()
                .map(ClassUtils::createInstance)
                .collect(Collectors.toList());
    }

    private void registerHandlerExecutions(Object handler) {
        Class clazz = handler.getClass();
        String classRequestMappingValue = getClassRequestMappingValue(clazz);

        List<Method> methods = getClassMethods(clazz);
        for (Method method : methods) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            Arrays.stream(annotation.method())
                    .forEach(requestMethod -> {
                        HandlerKey handlerKey = new HandlerKey(classRequestMappingValue + annotation.value(), requestMethod);
                        HandlerExecution handlerExecution = new HandlerExecution(handler, method);
                        handlerExecutions.put(handlerKey, handlerExecution);
                    });
        }
    }

    private String getClassRequestMappingValue(Class clazz) {
        if (clazz.isAnnotationPresent(RequestMapping.class)) {
            return ((RequestMapping) clazz.getAnnotation(RequestMapping.class)).value();
        }
        return "";
    }

    private List<Method> getClassMethods(Class clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .collect(Collectors.toList());
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }
}
