package nextstep.mvc.tobe;

import nextstep.web.annotation.Controller;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class ControllerScanner {
    private Map<Class<?>, Object> controllers = new HashMap<>();

    ControllerScanner() {
    }

    void initialize(Object... basePackage) throws IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class<?> controller : typesAnnotatedWith) {
            controllers.put(controller, controller.newInstance());
        }
    }

    Set<Class<?>> getKeys() {
        return controllers.keySet();
    }

    Object getController(Class<?> controllerType) {
        return controllers.get(controllerType);
    }
}
