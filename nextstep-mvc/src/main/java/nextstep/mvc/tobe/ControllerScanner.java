package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.utils.ClassUtils;
import nextstep.web.annotation.Controller;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.*;

public class ControllerScanner {

    private final Map<Class<?>, Object> controllers = Maps.newHashMap();

    public void scan(final Object... basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllerClazz = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class<?> clazz : controllerClazz) {
            controllers.put(clazz, ClassUtils.createNewInstance(clazz));
        }
    }

    public List<Method> getAllDeclaredMethods() {
        List<Method> methods = new ArrayList<>();
        for (Class<?> clazz : controllers.keySet()) {
            methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        }
        return methods;
    }

    public Object get(final Class<?> clazz) {
        return controllers.get(clazz);
    }
}
