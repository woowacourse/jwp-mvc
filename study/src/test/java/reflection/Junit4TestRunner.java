package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
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
        if (method.isAnnotationPresent(MyTest.class)) {
            method.invoke(new Junit4Test());
            return true;
        }
        return false;
    }
}
