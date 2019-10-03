package nextstep.mvc.tobe.scanner;

import nextstep.mvc.tobe.core.FailToComponentScanException;
import nextstep.mvc.tobe.core.HandlerExecution;
import nextstep.mvc.tobe.core.HandlerKey;
import nextstep.web.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerScanner {
    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);

    private Reflections reflections;

    public ControllerScanner(Reflections reflections) {
        this.reflections = reflections;
    }

    public Map<HandlerKey, HandlerExecution> scan() {
        Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();
        RequestMappingScanner requestMappingScanner = new RequestMappingScanner(reflections);

        for (Class<?> clazz : reflections.getTypesAnnotatedWith(Controller.class)) {
            Object target = getInstance(clazz);
            putKeyAndValue(handlerExecutions, requestMappingScanner.scan(clazz, target));
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

    private void putKeyAndValue(Map<HandlerKey, HandlerExecution> handlers, List<Map.Entry<HandlerKey, HandlerExecution>> entries) {
        for (Map.Entry<HandlerKey, HandlerExecution> entry : entries) {
            handlers.put(entry.getKey(), entry.getValue());
        }
    }
}
