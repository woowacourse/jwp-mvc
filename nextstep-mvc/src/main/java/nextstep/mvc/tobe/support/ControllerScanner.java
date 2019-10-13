package nextstep.mvc.tobe.support;

import nextstep.mvc.tobe.exception.InstanceCreationFailedException;
import nextstep.web.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerScanner  {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);
    private final Reflections reflections;
    private Map<Class<?>, Object> controllers;

    public ControllerScanner(Object... baseUrl) {
        reflections = new Reflections(baseUrl);
        controllers = scanController();
    }

    private Map<Class<?>, Object> scanController() {
        return reflections.getTypesAnnotatedWith(Controller.class).stream()
                .collect(Collectors.toMap(
                        clazz -> clazz,
                        clazz -> createInstance(clazz),
                        (p1, p2) -> p1 + ";" + p2)
                );
    }

    private Object createInstance(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.debug("Error: {}", e);
        }
        throw new InstanceCreationFailedException("Error: 인스턴스 생성 실패");
    }

    public Object getInstance(Class<?> clazz) {
        return controllers.get(clazz);
    }

    public Map<Class<?>, Object> getControllers() {
        return controllers;
    }
}
