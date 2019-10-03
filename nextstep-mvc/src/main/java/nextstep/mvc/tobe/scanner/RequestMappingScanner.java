package nextstep.mvc.tobe.scanner;

import nextstep.mvc.tobe.core.HandlerExecution;
import nextstep.mvc.tobe.core.HandlerKey;
import nextstep.web.annotation.RequestMapping;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestMappingScanner {
    private Reflections reflections;

    public RequestMappingScanner(Reflections reflections) {
        this.reflections = reflections;
    }

    public List<Map.Entry<HandlerKey, HandlerExecution>> scan(Class<?> clazz, Object target) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(this::hasParamsTypeOfRequestAndResponse)
                .map(method -> getHandlerExecutionsEntry(target, method))
                .collect(Collectors.toList());
    }

    private boolean hasParamsTypeOfRequestAndResponse(Method method) {
        return Arrays.stream(method.getParameterTypes())
                .allMatch(type -> type.equals(HttpServletRequest.class) || type.equals(HttpServletResponse.class));
    }

    private Map.Entry<HandlerKey, HandlerExecution> getHandlerExecutionsEntry(Object target, Method method) {
        RequestMapping mapping = method.getDeclaredAnnotation(RequestMapping.class);
        HandlerExecution handlerExecution = new HandlerExecution(target, method);
        return new AbstractMap.SimpleEntry<>(HandlerKey.of(mapping.value(), mapping.method()), handlerExecution);
    }
}
