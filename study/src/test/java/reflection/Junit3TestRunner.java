package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Junit3TestRunner {

    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;

        Method[] methods = clazz.getDeclaredMethods();
        Object obj = clazz.getDeclaredConstructor().newInstance();

        for (Method method : methods) {
            if (method.getName().indexOf("test")==0){
                method.invoke(obj);
            }
        }
    }
}
