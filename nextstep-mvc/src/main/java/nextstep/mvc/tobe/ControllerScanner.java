package nextstep.mvc.tobe;

import nextstep.ConsumerWithException;
import nextstep.web.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ControllerScanner {
    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);
    private final Map<Class<?>, Object> controllers = new HashMap<>();

    private final Reflections reflections;

    ControllerScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
        initialize();
    }

    private void initialize() {
        reflections.getTypesAnnotatedWith(Controller.class).forEach(wrapper(clazz -> controllers.put(clazz, clazz.newInstance())));
    }

    private <T extends Class, E extends Exception> Consumer<T> wrapper(ConsumerWithException<T, E> fe) {
        return arg -> {
            try {
                log.debug("controllerClazz : {}", arg);
                fe.apply(arg);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException();
            }
        };
    }

    Map<Class<?>, Object> getControllers() {
        return controllers;
    }
}
