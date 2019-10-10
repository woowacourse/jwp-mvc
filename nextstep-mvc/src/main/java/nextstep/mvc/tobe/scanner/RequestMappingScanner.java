package nextstep.mvc.tobe.scanner;

import nextstep.mvc.tobe.handler.HandlerExecution;
import nextstep.mvc.tobe.handler.HandlerKey;
import nextstep.mvc.tobe.handler.RequestMappingHandlerExecution;
import nextstep.mvc.tobe.handlerresolver.ClassInitializeException;
import nextstep.web.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestMappingScanner {
    private static final Logger log = LoggerFactory.getLogger(RequestMappingScanner.class);

    // todo : refacoring 필요
    public static Map<HandlerKey, HandlerExecution> scan(Set<Class<?>> annotatedClazz) {
        Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();

        for (Class<?> clazz : annotatedClazz) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    HandlerKey key = new HandlerKey(requestMapping.value(), requestMapping.method());
                    HandlerExecution execution = null;
                    try {
                        execution = new RequestMappingHandlerExecution(method, method.getDeclaringClass().getConstructor().newInstance());
                    } catch (Exception e) {
                        log.debug(e.getMessage(), e.getCause());
                        throw new ClassInitializeException();
                    }
                    handlerExecutions.put(key, execution);
                }
            }
        }
        return handlerExecutions;
    }
}
