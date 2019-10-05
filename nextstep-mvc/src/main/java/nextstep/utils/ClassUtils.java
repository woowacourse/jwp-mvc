package nextstep.utils;

import nextstep.utils.exception.ClassToInstanceFailedException;

import java.lang.reflect.InvocationTargetException;

public class ClassUtils {
    public static Object classToInstance(Class<?> classReflection) {
        try {
            return classReflection.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new ClassToInstanceFailedException(e);
        }
    }
}
