package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        final Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        // TODO Question 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
        Stream.of(clazz.getDeclaredFields()).forEach(x -> logger.debug("field: {}", x.toString()));
        Stream.of(clazz.getDeclaredConstructors()).forEach(x -> logger.debug("Constructor: {}", x.toString()));
        Stream.of(clazz.getDeclaredMethods()).forEach(x -> logger.debug("method: {}", x.toString()));
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        // TODO 인자를 가진 생성자를 활용해 인스턴스를 생성한다.
        for (final Constructor<?> constructor : Question.class.getConstructors()) {
            logger.debug("constructor: {}", constructor);
            final List<Object> paramTypes = new ArrayList<>();
            for (final Class<?> parameterType : constructor.getParameterTypes()) {
                if (parameterType.isPrimitive()) {
                    paramTypes.add(parameterType.getTypeName().equals("boolean") ? true : 0);
                } else {
                    paramTypes.add(parameterType.newInstance());
                }
            }
            final Object object = constructor.newInstance(paramTypes.toArray());
            logger.debug(object.toString());
        }
    }

    @Test
    public void privateFieldAccess() {
        final Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        // TODO Student private field에 값을 저장하고 조회한다.
        try {
            final Field field = clazz.getDeclaredField("name");
            final Student student = new Student();
            final String name = "재성";
            field.setAccessible(true);
            field.set(student, name);
            assertThat(student.getName()).isEqualTo(name);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
