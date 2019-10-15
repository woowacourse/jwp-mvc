package nextstep.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClassUtils {

    public static Object getNewInstance(Constructor constructor, Object... objects) {
        try {
            return constructor.newInstance(objects);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("fail to create new instance");
        }
    }
}
