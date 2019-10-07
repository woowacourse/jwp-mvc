package nextstep.mvc.tobe;

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
        reflections.getTypesAnnotatedWith(Controller.class).forEach(
                wrapper(clazz -> controllers.put(clazz, clazz.newInstance()))
        );
    }

    private <T extends Class, E extends Exception> Consumer<Class<?>> wrapper(FunctionWithException<T, E> fe) {
        return arg -> {
            try {
                fe.apply((T) arg);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        };
    }

    Map<Class<?>, Object> getControllers() {
        return controllers;
    }

    @FunctionalInterface
    public interface FunctionWithException<T, E extends Exception> {
        void apply(T t) throws E;
    }
}
