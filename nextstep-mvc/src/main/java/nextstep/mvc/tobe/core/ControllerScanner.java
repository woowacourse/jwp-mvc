package nextstep.mvc.tobe.core;

import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ControllerScanner implements ComponentScanner {
    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);

    private Reflections reflections;

    public ControllerScanner(Object... basePackages) {
        this.reflections = new Reflections(basePackages);
    }

    @Override
    public Map<HandlerKey, HandlerExecution> scan() {
        Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();

        for (Class<?> clazz : reflections.getTypesAnnotatedWith(Controller.class)) {
            Object target = getInstance(clazz);
            Arrays.stream(clazz.getDeclaredMethods())
                    .filter(this::hasParamsTypeOfRequestAndResponse)
                    .map(method -> getHandlerExecutionsEntry(target, method))
                    .forEach(entry -> handlerExecutions.put(entry.getKey(), entry.getValue()));
        }
        return handlerExecutions;
    }

    private Object getInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(e.getMessage());
            throw new FailToComponentScanException(e.getMessage());
        }
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
