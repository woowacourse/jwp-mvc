package nextstep.mvc.tobe.scanner;

import nextstep.mvc.tobe.core.HandlerExecution;
import nextstep.mvc.tobe.core.HandlerKey;
import nextstep.web.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class RequestMappingScanner {

    public Map<HandlerKey, HandlerExecution> scan(Map<Class<?>, Object> controllers) {
        Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();
        for (Map.Entry<Class<?>, Object> entry : controllers.entrySet()) {
            Arrays.stream(entry.getKey().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .flatMap(method -> getHandlerExecutionsEntry(entry.getValue(), method))
                    .forEach(handler -> handlerExecutions.put(handler.getKey(), handler.getValue()));
        }
        return handlerExecutions;
    }

    private Stream<Map.Entry<HandlerKey, HandlerExecution>> getHandlerExecutionsEntry(Object target, Method method) {
        RequestMapping mapping = method.getDeclaredAnnotation(RequestMapping.class);
        HandlerExecution handlerExecution = new HandlerExecution(target, method);
        return Arrays.stream(mapping.method())
                .map(requestMethod -> new AbstractMap.SimpleEntry<>(HandlerKey.of(mapping.value(), requestMethod),
                        handlerExecution));
    }
}
