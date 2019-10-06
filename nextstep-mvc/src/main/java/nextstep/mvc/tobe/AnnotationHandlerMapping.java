package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
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
        Set<Method> methods = getRequestMappingMethods(controllers.keySet());

        methods.forEach(method -> addHandlerExecution(controllers, method));
    }

    private void addHandlerExecution(Map<Class<?>, Object> controllers, Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        RequestMethod[] requestMethods = requestMapping.method();

        if (requestMethods.length == EMPTY_REQUEST_METHOD_COUNT) {
            requestMethods = RequestMethod.values();
        }

        Arrays.stream(requestMethods)
                .forEach(requestMethod -> {
                    HandlerKey handlerKey = new HandlerKey(requestMapping.value(), requestMethod);
                    Object clazz = controllers.get(method.getDeclaringClass());
                    handlerExecutions.put(handlerKey, new HandlerExecution(method, clazz));
                });
    }

    private Set<Method> getRequestMappingMethods(Set<Class<?>> clazzes) {
        Set<Method> methods = Sets.newHashSet();
        clazzes.forEach(clazz ->
                methods.addAll(getRequestMappingAnnotations(clazz.getDeclaredMethods())));
        return methods;
    }

    private Set<Method> getRequestMappingAnnotations(Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .collect(Collectors.toSet());
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
