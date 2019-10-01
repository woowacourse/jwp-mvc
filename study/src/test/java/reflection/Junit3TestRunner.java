package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Junit3TestRunner {
    private static final Logger log = LoggerFactory.getLogger(Junit3TestRunner.class);

    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;

        // Junit3Test에서 test로 시작하는 메소드 실행

        Object object = clazz.getDeclaredConstructor().newInstance();
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .forEach(method -> {
                    try {
                        method.invoke(object);
                    } catch (Exception e) {
                        System.out.println(e.getCause());
                    }
                });

    }
}
