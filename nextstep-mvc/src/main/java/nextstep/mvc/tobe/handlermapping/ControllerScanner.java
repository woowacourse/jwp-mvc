package nextstep.mvc.tobe.handlermapping;

import nextstep.mvc.tobe.exception.ControllerCreateException;
import nextstep.web.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);
    private final Reflections reflections;
    private final Map<Class<?>, Object> controllers = new HashMap<>();

    public ControllerScanner(Object[] basePackage) {
        this.reflections = new Reflections(basePackage);
        instantiateControllers(scanningControllers());
    }

    private void instantiateControllers(Set<Class<?>> controllers) {
        for (Class<?> controller : controllers) {
            try {
                this.controllers.put(controller, controller.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("{} 컨트롤러를 생성할 수 없습니다. {}", controller.getName(), e.getMessage());
                throw new ControllerCreateException();
            }
        }
    }

    private Set<Class<?>> scanningControllers() {
        return reflections.getTypesAnnotatedWith(Controller.class);
    }

    public Map<Class<?>, Object> getControllers() {
        return this.controllers;
    }
}
