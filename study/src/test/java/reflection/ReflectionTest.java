package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> logger.info("[field] : {}", field));
        Arrays.stream(clazz.getDeclaredConstructors()).forEach(constructor -> logger.info("[constructor] : {}", constructor));
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> logger.info("[method] : {}", method));
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("param length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }
        }

        Constructor constructor1 = clazz.getDeclaredConstructor(String.class, String.class, String.class);
        Question question1 = (Question) constructor1.newInstance("a", "b", "c");
        logger.info("[constructor] : {}", constructor1);
        logger.info("[question] : {}", question1);

        Constructor constructor2 = clazz.getDeclaredConstructor(long.class, String.class, String.class, String.class, Date.class, int.class);
        Question question2 = (Question) constructor2.newInstance(1, "a", "b", "c", new Date(), 1);
        logger.info("[constructor] : {}", constructor2);
        logger.info("[question] : {}", question2);


    }

    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        try {
            Field name = getAccessibleField(clazz, "name");
            Field age = getAccessibleField(clazz, "age");

            Student student = new Student();
            name.set(student, "pobi");
            age.set(student, 20);

            assertThat(student.getName()).isEqualTo("pobi");
            assertThat(student.getAge()).isEqualTo(20);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
    }

    private <T> Field getAccessibleField(Class<T> clazz, String fieldName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        return field;
    }
}
