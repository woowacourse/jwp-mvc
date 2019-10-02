package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final String FIELD_NAME = "name";
    private static final String FIELD_AGE = "age";

    private static final String NAME = "재성";
    private static final int AGE = 50;

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> logger.debug("field : {}", field));
        Arrays.stream(clazz.getDeclaredConstructors())
                .forEach(constructor -> logger.debug("constructor : {}", constructor));
        Arrays.stream(clazz.getDeclaredMethods())
                .forEach(method -> logger.debug("method : {}", method));
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

        // TODO 인자를 가진 생성자를 활용해 인스턴스를 생성한다.
    }

    @Test
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Field name = clazz.getDeclaredField(FIELD_NAME);
        Field age = clazz.getDeclaredField(FIELD_AGE);

        name.setAccessible(true);
        age.setAccessible(true);

        Student student = clazz.newInstance();
        name.set(student, NAME);
        age.set(student, AGE);

        assertThat(student.getName()).isEqualTo(NAME);
        assertThat(student.getAge()).isEqualTo(AGE);
    }
}
