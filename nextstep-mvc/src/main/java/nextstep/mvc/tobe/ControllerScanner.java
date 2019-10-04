package nextstep.mvc.tobe;

import nextstep.web.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);
    private final Map<Class<?>, Object> controllers = new HashMap<>();

    private final Reflections reflections;

    ControllerScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
        initialize();
    }

    private void initialize() {
        reflections.getTypesAnnotatedWith(Controller.class).forEach(controllerClazz -> {
            log.debug("controllerClazz : {}", controllerClazz);
            try {
                controllers.put(controllerClazz, controllerClazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(e.getMessage());
            }
        });
    }

    Map<Class<?>, Object> getControllers() {
        return controllers;
    }
}
