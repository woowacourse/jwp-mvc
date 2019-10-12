package reflection;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class Junit3TestRunner extends AbstractTestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Junit3Test test = clazz.getConstructor().newInstance();

        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .forEach(method -> invoke(method, test));
    }
}
