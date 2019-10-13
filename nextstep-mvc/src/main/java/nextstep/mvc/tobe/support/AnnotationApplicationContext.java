package nextstep.mvc.tobe.support;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationApplicationContext<T> extends ApplicationContext {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);
    private final Reflections reflections;
    private Map<Class<?>, Object> beans = new HashMap<>();

    public AnnotationApplicationContext(Object... basePackage) {
        super(basePackage);
        reflections = new Reflections(basePackage);
    }

    @Override
    public void scanBeans(Class... classes) {
        for (Class clazz : classes) {
            Set<Class<? extends T>> scanClasses = reflections.getTypesAnnotatedWith(clazz);
            beans.putAll(classInstanceMapping(scanClasses));
        }
    }

    Map<Class<?>, Object> classInstanceMapping(Set<Class<? extends T>> classes) {
        return classes.stream().collect(Collectors.toMap(
                a -> a,
                this::createInstance,
                (p1, p2) -> p1 + ";" + p2)
        );
    }

    public Object getInstance(Class<?> clazz) {
        return beans.get(clazz);
    }

    public Map<Class<?>, Object> getBeans() {
        return beans;
    }

}
