package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Junit3TestRunner {
    private static final Logger log = LoggerFactory.getLogger(Junit3TestRunner.class);

    @Test
    public void run() {
        Class<Junit3Test> clazz = Junit3Test.class;

        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .forEach(method -> {
                    try {
                        method.invoke(clazz.newInstance());
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                        log.error("error: {}", e.getMessage());
                    }
                });

    }
}
