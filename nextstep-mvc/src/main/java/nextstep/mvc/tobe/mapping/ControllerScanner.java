package nextstep.mvc.tobe.mapping;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);

    private final Map<Class<?>, Object> controllers;

    public ControllerScanner(Object[] basePackage) {
        controllers = Maps.newHashMap();
        scan(basePackage);
    }

    private void scan(Object[] basePackage) {
        Reflections reflection = new Reflections(basePackage);
        Set<Class<?>> annotated = reflection.getTypesAnnotatedWith(Controller.class);

        for (Class<?> controller : annotated) {
            try {
                controllers.put(controller, controller.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                logger.debug(e.getMessage());
            }
        }
    }

    public Object getController(Class<?> key) {
        return controllers.get(key);
    }

    public Set<Class<?>> getControllers() {
        return Collections.unmodifiableMap(controllers).keySet();
    }
}
