package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        final Class<Question> clazz = Question.class;

        // TODO Question 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
        Stream.of(clazz.getDeclaredFields()).forEach(x -> logger.debug(x.toString()));
        Stream.of(clazz.getConstructors()).forEach(x -> logger.debug(x.toString()));
        Stream.of(clazz.getMethods()).forEach(x -> logger.debug(x.toString()));

    }

    @Test
    public void privateFieldAccess() {
        final Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        // TODO Student private field에 값을 저장하고 조회한다.
        try {
            final Field field = clazz.getDeclaredField("name");
            field.setAccessible(true);
            final Student student = new Student();
            field.set(student, "재성");
            logger.debug(student.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        // TODO 인자를 가진 생성자를 활용해 인스턴스를 생성한다.
        for (final Constructor constructor : Question.class.getConstructors()) {
            final List<Object> paramTypes = new ArrayList<>();
            for (final Class<?> paramType : constructor.getParameterTypes()) {
                if (paramType.isPrimitive()) {
                    paramTypes.add(paramType.getTypeName().equals("boolean") ? true : 0);
                } else {
                    paramTypes.add(paramType.getConstructor().newInstance());
                }
            }
            logger.debug(constructor.newInstance(paramTypes.toArray()).toString());
        }
    }
}
