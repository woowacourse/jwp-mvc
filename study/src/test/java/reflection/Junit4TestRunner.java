package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        final Class<Junit4Test> clazz = Junit4Test.class;
        final Junit4Test junit4Test = clazz.getConstructor().newInstance();

        // TODO Junit4Test에서 @MyTest 애노테이션이 있는 메소드 실행
        Stream.of(clazz.getMethods())
                .filter(x -> x.isAnnotationPresent(MyTest.class))
                .forEach(x -> {
                    try {
                        x.invoke(junit4Test);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }
}
