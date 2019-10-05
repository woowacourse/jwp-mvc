package nextstep.utils;

import java.lang.reflect.InvocationTargetException;

public class ClassUtils {
    public static Object newInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            throw new InstanceCreationFailedException(e);
        }
    }
}
