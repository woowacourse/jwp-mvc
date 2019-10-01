package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (getMethodName(method).startsWith("test")) {
                method.invoke(clazz.newInstance());
            }
        }
    }

    private String getMethodName(Method method) {
        String[] tokens = method.toGenericString().split(" ");
        String[] structures = tokens[2].split("\\.");
        return structures[2];
    }
}
