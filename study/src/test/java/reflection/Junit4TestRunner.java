package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class Junit4TestRunner {

    private static final Logger log = LoggerFactory.getLogger(Junit4TestRunner.class);

    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        for (Method method : clazz.getDeclaredMethods()) {
            log.info("method : {}", method);
            if (method.isAnnotationPresent(MyTest.class)) {
                log.info("annotation : {}", method.getAnnotation(MyTest.class));
                method.invoke(clazz.newInstance());
            }
        }
    }
}
