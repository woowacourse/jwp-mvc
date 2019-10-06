package nextstep.utils;

import nextstep.mvc.tobe.exception.InstanceCreationFailedException;

import java.lang.reflect.InvocationTargetException;

public class ClassUtils {
    private ClassUtils() {
    }

    public static Object createNewInstance(final Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InstanceCreationFailedException(e);
        }
    }
}
