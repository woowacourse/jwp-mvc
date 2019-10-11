package nextstep.mvc.tobe;

import nextstep.utils.ClassUtils;
import org.reflections.Reflections;

import java.util.Set;
import java.util.stream.Collectors;

public class ClassScanner {
    public static <T> Set<T> scanSubTypesOf(Class<T> type, Object... basePackage) {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getSubTypesOf(type).stream()
                .map(ClassUtils::classToInstance)
                .collect(Collectors.toSet());
    }
}
