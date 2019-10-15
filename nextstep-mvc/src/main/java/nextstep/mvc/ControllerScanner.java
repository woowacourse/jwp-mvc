package nextstep.mvc;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {

    public static Map<Class<?>, Object> scan(Class<? extends Annotation> annotation, Object[] basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(annotation);
        return scanController(controllers);
    }

    private static Map<Class<?>, Object> scanController(Set<Class<?>> controllers) {
        Map<Class<?>, Object> instancePool = new HashMap<>();
        for (Class<?> controller : controllers) {
            try {
                instancePool.put(controller, controller.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return instancePool;
    }
}
