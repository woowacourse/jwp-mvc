package nextstep.mvc;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationScanner {
    public static Map<Class<?>, Object> scan(Class clazz, Object... basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(clazz);
        return createInstanceMapping(annotated);
    }

    private static Map<Class<?>, Object> createInstanceMapping(Set<Class<?>> annotated) {
        Map<Class<?>, Object> mapping = new HashMap<>();
        try {
            for (Class clazz : annotated) {
                mapping.put(clazz, clazz.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return mapping;
    }
}
