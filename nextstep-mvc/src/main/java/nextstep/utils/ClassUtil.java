package nextstep.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ConcurrentModificationException;

public class ClassUtil {
    public static Object getNewInstance(Class<?> type) {
        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("fail to create new instance");
        }
    }

    public static Object getNewInstanceWithConstructor(Constructor constructor, Object... objects) {
        try {
            return constructor.newInstance(objects);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("fail to create new instance");
        }
    }
}
