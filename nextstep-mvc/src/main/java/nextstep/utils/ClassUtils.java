package nextstep.utils;

import java.lang.reflect.Constructor;
import nextstep.mvc.exception.InstantiationException;

public class ClassUtils {
    public static Object createInstance(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch(Exception e) {
            throw new InstantiationException();
        }
    }

}
