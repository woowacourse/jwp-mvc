package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final int EMPTY_REQUEST_METHOD_COUNT = 0;
    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        Map<Class<?>, Object> controllers = new ControllerScanner(basePackage).getControllers();
        Map<Class<?>, Set<Method>> requestMappingMethods = getRequestMappingMethods(controllers.keySet());

        requestMappingMethods.keySet().forEach(clazz -> {
            for (Method method : requestMappingMethods.get(clazz)) {
                createHandlerKeys(method.getAnnotation(RequestMapping.class)).forEach(handlerKey ->
                        handlerExecutions.put(handlerKey, new HandlerExecution(method, controllers.get(clazz))));
            }
        });
    }

    private Map<Class<?>, Set<Method>> getRequestMappingMethods(Set<Class<?>> clazzes) {
        Map<Class<?>, Set<Method>> methods = Maps.newHashMap();
        clazzes.forEach(clazz ->
                methods.put(clazz, getRequestMappingAnnotations(clazz.getDeclaredMethods())));
        return methods;
    }

    private Set<Method> getRequestMappingAnnotations(Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .collect(Collectors.toSet());
    }

    private List<HandlerKey> createHandlerKeys(RequestMapping requestMapping) {
        RequestMethod[] requestMethods = requestMapping.method();
        if (requestMethods.length == EMPTY_REQUEST_METHOD_COUNT) {
            requestMethods = RequestMethod.values();
        }

        return Arrays.stream(requestMethods)
                .map(requestMethod -> new HandlerKey(requestMapping.value(), requestMethod))
                .collect(Collectors.toList());
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
