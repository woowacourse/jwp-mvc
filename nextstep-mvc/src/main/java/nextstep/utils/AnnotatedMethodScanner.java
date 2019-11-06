package nextstep.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotatedMethodScanner {
    private AnnotatedMethodScanner() {
    }

    private static class SingletonHolder {
        public static final AnnotatedMethodScanner INSTANCE = new AnnotatedMethodScanner();
    }

    public static AnnotatedMethodScanner getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<Method> scan(List<Class<?>> targetClasses, Class<? extends Annotation> annotationClass) {
        return targetClasses.stream()
                .flatMap(targetClass -> scan(targetClass, annotationClass).stream())
                .collect(Collectors.toList());
    }

    public List<Method> scan(Class<?> targetClass, Class<? extends Annotation> annotationClass) {
        List<Method> declaredMethods = Arrays.asList(targetClass.getDeclaredMethods());

        return filterAnnotatedMethod(declaredMethods, annotationClass);
    }

    private List<Method> filterAnnotatedMethod(List<Method> declaredMethods, Class<? extends Annotation> annotationClass) {
        return declaredMethods.stream()
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .collect(Collectors.toList());
    }
}
