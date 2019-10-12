package reflection;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class Junit4TestRunner extends AbstractTestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Junit4Test test = clazz.getConstructor().newInstance();

        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(MyTest.class))
                .forEach(method -> invoke(method, test));
    }
}
