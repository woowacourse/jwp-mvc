package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        final Class<Junit3Test> clazz = Junit3Test.class;
        final Junit3Test junit3Test = clazz.getConstructor().newInstance();

        // TODO Junit3Test에서 test로 시작하는 메소드 실행
        Stream.of(clazz.getDeclaredMethods())
                .filter(x -> x.getName().startsWith("test"))
                .forEach(x -> {
                    try {
                        x.invoke(junit3Test);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }
}
