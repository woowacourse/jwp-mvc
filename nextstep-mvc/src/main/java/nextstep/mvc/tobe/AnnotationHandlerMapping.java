package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

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

    private List<HandlerKey> createHandlerKeys(RequestMapping requestMapping) {
        RequestMethod[] requestMethods = requestMapping.method();
        if (requestMethods.length == 0) {
            requestMethods = RequestMethod.values();
        }

        return Arrays.stream(requestMethods)
                .map(requestMethod -> new HandlerKey(requestMapping.value(), requestMethod))
                .collect(Collectors.toList());
    }

    private Map<Class<?>, Set<Method>> getRequestMappingMethods(Set<Class<?>> clazzes) {
        Map<Class<?>, Set<Method>> list = new HashMap<>();
        clazzes.forEach(clazz ->
                list.put(clazz, ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class))));
        return list;
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        log.debug("path: {}", request.getRequestURI());
        log.debug("path: {}", RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
