package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Method[] methods = clazz.getDeclaredMethods();
        int count = 0;

        for (Method method : methods) {
            if(runTest(method)) {
                count++;
            }
        }

        assertThat(count).isEqualTo(2);
    }

    private boolean runTest(Method method) throws IllegalAccessException, InvocationTargetException {
        if (method.getName().startsWith("test")) {
            method.invoke(new Junit3Test());
            return true;
        }
        return false;
    }
}
