package nextstep.utils;

import nextstep.mvc.exception.InstantiationException;

import java.lang.reflect.Constructor;

public class ClassUtils {
    public static <T> T createInstance(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new InstantiationException();
        }
    }

}
