package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.controllermapper.ParameterAdapters;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
    private static final int EMPTY_METHODS = 0;

    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
    private ParameterAdapters parameterAdapters;

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Map<Class<?>, Object> controllers = new ControllerScanner(basePackage).getControllers();
        Map<Class<?>, Set<Method>> requestMappingMethods = getRequestMappingMethods(controllers.keySet());
        this.parameterAdapters = new ParameterAdapters();

        requestMappingMethods.keySet()
                .forEach(clazz -> turnRequestMappingMethods(controllers.get(clazz), requestMappingMethods.get(clazz)));
    }

    private void turnRequestMappingMethods(Object object, Set<Method> methods) {
        methods.forEach(method -> putHandlerExecution(object, method));
    }

    private void putHandlerExecution(Object instance, Method method) {
        createHandlerKeys(method.getAnnotation(RequestMapping.class)).forEach(handlerKey ->
                handlerExecutions.put(handlerKey, new HandlerExecution(method, instance, parameterAdapters)));
    }

    private Map<Class<?>, Set<Method>> getRequestMappingMethods(Set<Class<?>> clazzes) {
        return clazzes.stream()
                .collect(Collectors.toMap(clazz -> clazz, clazz -> ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class))));
    }

    private List<HandlerKey> createHandlerKeys(RequestMapping requestMapping) {
        return Arrays.stream(getRequestMethods(requestMapping))
                .map(requestMethod -> new HandlerKey(requestMapping.value(), requestMethod))
                .collect(Collectors.toList());
    }

    private RequestMethod[] getRequestMethods(RequestMapping requestMapping) {
        RequestMethod[] requestMethods = requestMapping.method();
        if (requestMethods.length == EMPTY_METHODS) {
            requestMethods = RequestMethod.values();
        }
        return requestMethods;
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        log.debug("uri: {} method: {}", request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
