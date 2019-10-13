package nextstep.utils;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ComponentScanner {
    private static final Logger log = LoggerFactory.getLogger(ComponentScanner.class);

    private final Reflections reflections;

    private ComponentScanner(Reflections reflections) {
        this.reflections = reflections;
    }

    public static ComponentScanner fromBasePackagePrefix(String prefix) {
        return new ComponentScanner(new Reflections(prefix));
    }

    public Map<Class<?>, Object> scan(Class<? extends Annotation> annotation) {
        Map<Class<?>, Object> instances = new HashMap<>();

        for (Class<?> annotatedClass : scanAnnotatedClasses(annotation)) {
            try {
                instances.put(annotatedClass, annotatedClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error("error: ", e);
            }
        }

        return instances;
    }

    private Set<Class<?>> scanAnnotatedClasses(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation);
    }
}
