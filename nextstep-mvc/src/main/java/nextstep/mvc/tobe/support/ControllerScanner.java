package nextstep.mvc.tobe.support;

import nextstep.web.annotation.Controller;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private final Map<Class<?>, Object> controllers = new HashMap<>();
    private final Reflections reflections;

    public ControllerScanner(Object... basePackages)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        reflections = new Reflections(Arrays.asList(basePackages));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);
        for (final Class<?> clazz : classes) {
            controllers.put(clazz, clazz.getDeclaredConstructor().newInstance());
        }
    }

    public Set<Class<?>> getControllers() {
        return controllers.keySet();
    }

    public Object instantiate(final Class<?> key) {
        return controllers.get(key);
    }
}
