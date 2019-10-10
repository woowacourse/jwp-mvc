package nextstep.mvc.scanner;

import nextstep.mvc.core.FailToComponentScanException;
import nextstep.mvc.core.HandlerExecution;
import nextstep.mvc.core.HandlerKey;
import nextstep.web.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerScanner {
    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);

    private Reflections reflections;

    public ControllerScanner(Reflections reflections) {
        this.reflections = reflections;
    }

    public Map<HandlerKey, HandlerExecution> scan() {
        RequestMappingScanner requestMappingScanner = new RequestMappingScanner();

        Map<Class<?>, Object> collect = reflections.getTypesAnnotatedWith(Controller.class).stream()
                .collect(Collectors.toMap(clazz -> clazz, this::getInstance));

        return requestMappingScanner.scan(collect);
    }

    private Object getInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(e.getMessage());
            throw new FailToComponentScanException(e.getMessage());
        }
    }
}
