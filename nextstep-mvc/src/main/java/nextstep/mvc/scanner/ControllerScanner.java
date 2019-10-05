package nextstep.mvc.scanner;

import nextstep.web.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {

    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);
    private Map<Class<?>,Object> controllers = new HashMap<>();

    public ControllerScanner(final String classPath) {
        this.initialize(new Reflections(classPath));
    }

    private void initialize(Reflections reflections) {
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);
        classes.forEach(clazz -> {
            try {
                controllers.put(clazz, clazz.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }

    public Map<Class<?>, Object> getControllers() {
        return controllers;
    }


}
