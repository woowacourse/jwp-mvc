package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> testMethod = Arrays.asList(methods).stream()
                .filter(method -> method.getName().startsWith("test"))
                .collect(Collectors.toList());

        assertThat(testMethod.size()).isEqualTo(2);

        Junit3Test junit3Test = new Junit3Test();
        for (Method method : testMethod) {
            method.invoke(junit3Test);
        }
    }
}
