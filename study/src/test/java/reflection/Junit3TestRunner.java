package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class Junit3TestRunner {
    private static final String TEST = "test";

    @Test
    void run() throws Exception {
        final Class<Junit3Test> clazz = Junit3Test.class;

        // Junit3Test에서 test로 시작하는 메소드 실행
        for (final Method method : clazz.getMethods()) {
            final String methodName = method.getName();
            if (methodName.startsWith(TEST)) {
                method.invoke(clazz.getDeclaredConstructor().newInstance());
            }
        }
    }
}
