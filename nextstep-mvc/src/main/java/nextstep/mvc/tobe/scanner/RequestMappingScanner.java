package nextstep.mvc.tobe.scanner;

import nextstep.mvc.tobe.handler.CreateClassInstanceException;
import nextstep.mvc.tobe.handler.HandlerExecution;
import nextstep.mvc.tobe.handler.HandlerKey;
import nextstep.web.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestMappingScanner {
    private static final Logger log = LoggerFactory.getLogger(RequestMappingScanner.class);

    public static Map<HandlerKey, HandlerExecution> scan(Set<Class<?>> annotatedClazz) {
        Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();
        annotatedClazz.forEach(clazz -> mappingExecution(handlerExecutions, clazz));

        return handlerExecutions;
    }

    private static void mappingExecution(Map<HandlerKey, HandlerExecution> handlerExecutions, Class<?> clazz) {
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    HandlerKey key = new HandlerKey(requestMapping.value(), requestMapping.method());
                    HandlerExecution execution = makeRequestMappingHandlerExecution(method);
                    handlerExecutions.put(key, execution);
                });
    }

    private static HandlerExecution makeRequestMappingHandlerExecution(Method method) {
        try {
            return new HandlerExecution(method, method.getDeclaringClass().getConstructor().newInstance());
        } catch (Exception e) {
            log.debug(e.getMessage(), e.getCause());
            throw new CreateClassInstanceException();
        }
    }
}
