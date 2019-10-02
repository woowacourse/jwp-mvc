package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        logger.debug("getModifier: {} --- {}", clazz.getModifiers(), Modifier.toString(clazz.getModifiers()));
        logger.debug("\n");

        logger.debug("getFields: {}", Arrays.toString(clazz.getFields()));
        logger.debug("\n");

        logger.debug("getDeclaredFields: {}", Arrays.toString(clazz.getDeclaredFields()));
        logger.debug("\n");

        logger.debug("getConstructors: {}", Arrays.toString(clazz.getConstructors()));
        logger.debug("\n");

        logger.debug("getDeclaredConstructors: {}", Arrays.toString(clazz.getDeclaredConstructors()));
        logger.debug("\n");

        logger.debug("getMethods: {}", Arrays.toString(clazz.getMethods()));
        logger.debug("\n");

        logger.debug("getDeclaredMethods: {}", Arrays.toString(clazz.getDeclaredMethods()));

    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }
        }

        List<Object[]> argsList = (List<Object[]>) Arrays.asList(
            new Object[]{1L, "writer", "title", "contents", new Date(), 0},
            new Object[]{"writer", "title", "contents"}
        );

        for (Constructor constructor : constructors) {
            for (Object[] args : argsList) {
                if (constructor.getParameterCount() == args.length) {
                    Question question = (Question) constructor.newInstance(args);
                    logger.debug("question: {}", question);
                    logger.debug("args: {}", Arrays.asList(args));
                }
            }
        }
    }

    @Test
    public void privateFieldAccess() throws Exception {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();
        fields[0].setAccessible(true); // name
        fields[1].setAccessible(true); // age

        fields[0].set(student, "mr.Conas");
        fields[1].set(student, 20);

        logger.debug("name: {}", student.getName());
        logger.debug("age: {}", student.getAge());
    }
}
