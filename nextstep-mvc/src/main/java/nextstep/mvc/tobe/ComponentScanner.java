package nextstep.mvc.tobe;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ComponentScanner {

    private final Reflections reflections;

    public ComponentScanner(Reflections reflections) {
        this.reflections = reflections;
    }

    public static ComponentScanner of(Object... params) {
        return new ComponentScanner(new Reflections(params));
    }

    public Set<Class<?>> getClassesAnnotated(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation);
    }
}
