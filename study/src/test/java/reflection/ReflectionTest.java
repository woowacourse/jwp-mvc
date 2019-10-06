package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void privateFieldAccess() throws IllegalAccessException, NoSuchFieldException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = new Student();

        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(student, "Martin");

        Field age = clazz.getDeclaredField("age");
        age.setAccessible(true);
        age.set(student, 11);

        assertThat(student.toString()).isEqualTo("Student{name='Martin', age=11}");
    }
}
