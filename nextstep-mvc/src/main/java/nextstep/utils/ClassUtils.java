package nextstep.utils;

import java.lang.reflect.InvocationTargetException;

public class ClassUtils {
    public static Object createInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InstanceCreationFailedException(e);
        }
    }
}
