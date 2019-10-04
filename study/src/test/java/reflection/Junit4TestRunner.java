package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Junit4TestRunner {

    private static final Logger logger = LoggerFactory.getLogger(Junit4TestRunner.class);

    @Test
    public void run() {
        Class<Junit4Test> clazz = Junit4Test.class;

        List<Method> methods = Arrays.asList(clazz.getDeclaredMethods());
        methods.stream()
                .filter(method -> method.isAnnotationPresent(MyTest.class))
                .forEach(method -> {
                    try {
                        method.invoke(clazz.getDeclaredConstructor().newInstance());
                    } catch (Exception e) {
                        logger.error("Error while invoke method", e);
                    }
                });
    }
}
