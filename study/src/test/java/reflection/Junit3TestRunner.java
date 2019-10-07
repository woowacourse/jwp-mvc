package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Junit3TestRunner {

    private static final Logger logger = LoggerFactory.getLogger(Junit3TestRunner.class);

    @Test
    public void run() {
        Class<Junit3Test> clazz = Junit3Test.class;
        Method[] methods = clazz.getMethods();

        Arrays.stream(methods)
                .filter(method -> matchMethod(method.getName()))
                .forEach(this::invoke);
    }

    private void invoke(Method method) {
        try {
            method.invoke(method.getDeclaringClass().getConstructor().newInstance());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean matchMethod(String name) {
        Pattern pattern = Pattern.compile("test.*");
        Matcher matcher = pattern.matcher(name);
        return  matcher.find();
    }
}
