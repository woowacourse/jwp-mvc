package reflection;

import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    private static final Map<Class, Object> defaultMap = new HashMap<>();

    static {
        defaultMap.put(String.class, "abc");
        defaultMap.put(long.class, 1L);
        defaultMap.put(Date.class, DateUtil.now());
        defaultMap.put(int.class, 1);
    }

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
        // Question 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> logger.debug(field.toString()));
        logger.debug("\r\n");
        Arrays.stream(clazz.getDeclaredConstructors()).forEach(constructor -> logger.debug(constructor.toString()));
        logger.debug("\r\n");
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> logger.debug(method.toString()));
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("parameter length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("parameter type : {}", paramType);
            }
        }

        // 인자를 가진 생성자를 활용해 인스턴스를 생성한다.
        for (Constructor constructor : constructors) {
            List<Object> params = new ArrayList<>();
            for (Class parameterType : constructor.getParameterTypes()) {
                params.add(defaultMap.get(parameterType));
            }
            Question question = (Question) constructor.newInstance(params.toArray());
            logger.info(question.toString());
        }
    }

    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        // Student private field에 값을 저장하고 조회한다.
        try {
            Field name = clazz.getDeclaredField("name");
            Field age = clazz.getDeclaredField("age");
            name.setAccessible(true);
            age.setAccessible(true);
            Student student = new Student();
            name.set(student, "제이엠");
            age.set(student, 91);
            assertThat(student.getName()).isEqualTo("제이엠");
            assertThat(student.getAge()).isEqualTo(91);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
