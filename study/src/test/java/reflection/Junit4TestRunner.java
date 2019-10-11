package reflection;

import nextstep.ConsumerWithException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;

public class Junit4TestRunner {
    private static final Logger log = LoggerFactory.getLogger(Junit4TestRunner.class);

    @Test
    public void run() {
        Class<Junit4Test> clazz = Junit4Test.class;

        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(MyTest.class))
                .forEach(wrapper(method -> method.invoke(clazz.newInstance())));
    }

    private <T extends Method, E extends Exception> Consumer<T> wrapper(ConsumerWithException<T, E> fe) {
        return arg -> {
            try {
                fe.apply(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
