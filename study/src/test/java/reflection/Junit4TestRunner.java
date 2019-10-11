package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> testMethods = Arrays.asList(methods).stream()
                .filter(s -> s.isAnnotationPresent(MyTest.class))
                .collect(Collectors.toList());

        assertThat(testMethods.size()).isEqualTo(2);
        for (Method method : testMethods) {
            method.invoke(new Junit4Test());
        }
    }
}

