package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        Method[] methods = clazz.getDeclaredMethods();
        Constructor[] constructors = clazz.getDeclaredConstructors();
        Field[] fields =  clazz.getDeclaredFields();

        Arrays.asList(methods).forEach(x -> logger.debug(x.getName()));
        assertThat(methods.length).isEqualTo(11);

        Arrays.asList(constructors).forEach(x -> logger.debug("{}: {}",x,x.getParameterTypes().toString()));
        assertThat(constructors.length).isEqualTo(2);

        Arrays.asList(fields).forEach(x -> logger.debug(x.getName()));
        assertThat(fields.length).isEqualTo(6);
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
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        // TODO Student private field에 값을 저장하고 조회한다.
    }
}
