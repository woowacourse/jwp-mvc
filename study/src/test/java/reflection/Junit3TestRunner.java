package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            runTest(method);
        }
    }

    private void runTest(Method method) throws IllegalAccessException, InvocationTargetException {
        if (method.getName().startsWith("test")) {
            method.invoke(new Junit3Test());
        }
    }
}
