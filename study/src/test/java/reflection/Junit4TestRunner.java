package reflection;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        // Junit4Test에서 @MyTest 애노테이션이 있는 메소드 실행

        Object object = clazz.getDeclaredConstructor().newInstance();
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(MyTest.class))
                .forEach(method -> {
                    try {
                        method.invoke(object);
                    } catch (Exception e) {
                        System.out.println(e.getCause());
                    }
                });
    }
}
