package nextstep.utils;

import nextstep.utils.exception.ClassToInstanceFailedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClassUtils {
    public static <T> T classToInstance(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new ClassToInstanceFailedException(e);
        }
    }
}
