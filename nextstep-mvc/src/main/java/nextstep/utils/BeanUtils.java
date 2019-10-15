package nextstep.utils;

import nextstep.mvc.tobe.exception.InstanceCreationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class BeanUtils {
    static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public static <T> T createInstance(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.debug("Error: {}", e);
        }
        throw new InstanceCreationFailedException("Error: 인스턴스 생성 실패");
    }
}
