package nextstep.mvc.tobe;

import nextstep.mvc.exception.ControllerInstantiateException;
import nextstep.web.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerScanner {
    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);

    private Reflections reflection;
    private Map<Class<?>, Object> controllers;

    public ControllerScanner(Object... basePackage) {
        this.reflection = new Reflections(basePackage);
        this.controllers = new HashMap<>();

        instantiateControllers();
    }

    public Set<Class<?>> getControllers() {
        return controllers.keySet();
    }

    private void instantiateControllers() {
        Set<Class<?>> controllerAnnotations = reflection.getTypesAnnotatedWith(Controller.class);
        log.debug("controller:{}", controllerAnnotations);
        controllers = controllerAnnotations.stream().collect(Collectors.toMap(controller -> controller, controller -> {
            try {
                return controller.newInstance();
            } catch (Exception e) {
                throw new ControllerInstantiateException();
            }
        }));
    }
}