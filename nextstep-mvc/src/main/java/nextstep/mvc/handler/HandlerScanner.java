package nextstep.mvc.handler;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class HandlerScanner {
    private static final Logger logger = LoggerFactory.getLogger(HandlerScanner.class);
    private static final int EMPTY = 0;

    private final Reflections reflections;
    private Map<Class<?>, Object> instances = Maps.newHashMap();

    public HandlerScanner(Reflections reflections) {
        this.reflections = reflections;
    }

    public Map<HandlerKey, HandlerExecution> getHandlerExecutions() {
        Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
        Set<Class<?>> clazz = reflections.getTypesAnnotatedWith(Controller.class);
        clazz.stream()
                .peek(this::createInstance)
                .flatMap(c -> Arrays.stream(c.getDeclaredMethods())
                        .filter(m -> m.isAnnotationPresent(RequestMapping.class)))
                .forEach(m -> createHandlerExecution(m, handlerExecutions));
        return handlerExecutions;
    }

    private void createInstance(Class<?> c) {
        try {
            instances.put(c, c.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            logger.error("error: {}", ExceptionUtils.getStackTrace(e));
        }
    }

    private void createHandlerExecution(Method method, Map<HandlerKey, HandlerExecution> handlerExecutions) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        RequestMethod[] methods = getRequestMethods(requestMapping);
        String value = requestMapping.value();

        for (RequestMethod requestMethod : methods) {
            HandlerKey handlerKey = new HandlerKey(value, requestMethod);
            handlerExecutions.put(handlerKey,
                    new HandlerExecution(method, instances.get(method.getDeclaringClass())));
        }
    }

    private RequestMethod[] getRequestMethods(RequestMapping requestMapping) {
        RequestMethod[] methods = requestMapping.method();
        if (methods.length == EMPTY) {
            return RequestMethod.values();
        }
        return methods;
    }
}
