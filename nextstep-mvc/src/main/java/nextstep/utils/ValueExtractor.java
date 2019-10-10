package nextstep.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class ValueExtractor {
    private static final Logger log = LoggerFactory.getLogger(ValueExtractor.class);

    public static final Map<String, Object> EMPTY = Collections.unmodifiableMap(Collections.emptyMap());

    public static Map<String, Object> extractFromAnnotation(Annotation annotation, ValueTargets targets) {
        Class<? extends Annotation> annotationType = annotation.annotationType();

        long cnt = Arrays.asList(annotationType.getDeclaredMethods()).stream()
                .filter(method -> targets.exist(method.getName(), method.getReturnType())).count();

        return Arrays.asList(annotationType.getDeclaredMethods()).stream()
                .filter(method -> targets.exist(method.getName(), method.getReturnType()))
                .collect(Collectors.toMap(
                        method -> method.getName(),
                        method -> invokeWithoutFail(method, annotation)
                ));
    }

    private static Object invokeWithoutFail(Method method, Object obj) {
        try {
            return method.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.info("e: ", e);
            return null;
        }
    }
}
