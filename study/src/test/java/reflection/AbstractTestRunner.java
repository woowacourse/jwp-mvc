package reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractTestRunner {
    private static final Logger logger = LoggerFactory.getLogger(AbstractTestRunner.class);

    protected Object invoke(Method method, Object instance) {
        try {
            return method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.debug(e.getMessage());
            throw new RuntimeException();
        }
    }
}
