package nextstep.mvc.tobe.mapping;

import nextstep.mvc.tobe.exception.ControllerScannerException;
import nextstep.web.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerScanner {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);

    private final Map<Class<?>, Object> map;

    public ControllerScanner(final Object... basePackages) {
        final Reflections reflections = new Reflections(basePackages);

        final Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        this.map = controllers.stream()
                .map(clazz -> {
                    try {
                        logger.debug("class: {}, type: {}", clazz.getName(), clazz.getTypeName());
                        return clazz.getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new ControllerScannerException(e.getMessage());
                    }
                }).collect(Collectors.toMap(Object::getClass, instance -> instance));
    }

    public Object getInstance(final Class<?> clazz) {
        return map.get(clazz);
    }

    public Set<Class<?>> keySet() {
        return map.keySet();
    }
}
