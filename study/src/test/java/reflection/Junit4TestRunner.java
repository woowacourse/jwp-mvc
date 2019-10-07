package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Junit4TestRunner {

    private static final Logger logger = LoggerFactory.getLogger(Junit3TestRunner.class);

    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Method[] methods = clazz.getMethods();

        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(MyTest.class))
                .forEach(method -> invoke(method));
    }

    private void invoke(Method method) {
        try {
            method.invoke(method.getDeclaringClass().getConstructor().newInstance());
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
            logger.error(e.getMessage());
        }
    }
}
