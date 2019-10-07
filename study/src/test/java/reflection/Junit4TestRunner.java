package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            invokeAnnotationMethod(clazz, method);
        }
    }

    private void invokeAnnotationMethod(Class<Junit4Test> clazz, Method method)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (method.isAnnotationPresent(MyTest.class)) {
            method.invoke(clazz.newInstance());
        }
    }
}
