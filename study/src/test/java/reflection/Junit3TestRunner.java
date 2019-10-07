package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Junit3TestRunner {
    private static final String TEST = "test";

    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            invokeTestMethod(clazz, method);
        }
    }

    private void invokeTestMethod(Class<Junit3Test> clazz, Method method)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (method.getName().startsWith(TEST)) {
            method.invoke(clazz.newInstance());
        }
    }
}
