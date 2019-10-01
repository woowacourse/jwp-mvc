package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;

        Field[] fields = clazz.getDeclaredFields();
        logger.debug("===================== Fields");
        for (Field field : fields) {
            logger.debug(field.toGenericString());
        }

        Constructor[] constructors = clazz.getDeclaredConstructors();
        logger.debug("===================== Constructors");
        for (Constructor constructor : constructors) {
            logger.debug(constructor.toGenericString());
        }

        Method[] methods = clazz.getDeclaredMethods();
        logger.debug("===================== Methods");
        for (Method method : methods) {
            logger.debug(method.toString());
        }
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        Object[] parameters1 = {"a", "b", "c"};
        Object[] parameters2 = {1L, "a", "b", "c", new Date(), 1};

        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            Question question = null;
            if (parameterTypes.length == 3) {
                question = (Question) constructor.newInstance(parameters1);
            }

            if (parameterTypes.length == 6) {
                question = (Question) constructor.newInstance(parameters2);
            }
            logger.debug(question.toString());
        }
    }

    @Test
    public void privateFieldAccess() throws IllegalAccessException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Field[] fields = clazz.getDeclaredFields();

        Student student = new Student();

        for (Field field : fields) {
            if (isPrivateField(field)) {
                field.setAccessible(true);
                if (field.getName().equals("name")) {
                    field.set(student, "Martin");
                }
                if (field.getName().equals("age")) {
                    field.set(student, 1);
                }
            }
        }
        logger.debug(student.toString());
    }

    private boolean isPrivateField(Field field) {
        String[] tokens = field.toGenericString().split(" ");
        return "private".equals(tokens[0]);
    }
}
