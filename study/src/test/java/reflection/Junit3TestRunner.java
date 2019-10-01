package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        // TODO Junit3Test에서 test로 시작하는 메소드 실행
        Class<Junit3Test> clazz = Junit3Test.class;
        Junit3Test instance = clazz.getDeclaredConstructor().newInstance();

        Method[] methods = clazz.getDeclaredMethods();
        Arrays.stream(methods)
            .filter(method -> method.getName().startsWith("test"))
            .forEach(method -> {
                try {
                    method.invoke(instance);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
    }
}
