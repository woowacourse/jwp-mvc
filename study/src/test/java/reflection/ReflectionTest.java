package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() throws NoSuchMethodException {
        Class<Question> clazz = Question.class;
        logger.debug("클래스 이름 : {} ", clazz.getName());

        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields)
            .forEach(field -> logger.debug("필드 이름 : {}, 필드 타입 : {}", field.getName(), field.getType()));

        Arrays.stream(clazz.getConstructors())
            .forEach(constructor -> logger.debug("생성자 이름: {} 생성자: {}", constructor.getName(), constructor));
        logger.debug(clazz.getConstructor(String.class, String.class, String.class).toString());

        Arrays.stream(clazz.getMethods())
            .forEach(method -> logger.debug("메소드 이름: {}, 파라미터: {}, 리턴 타입: {}", method.getName(), method.getParameterCount(), method.getReturnType()));

        Arrays.stream(clazz.getDeclaredMethods())
            .forEach(method -> logger.debug("메소드 이름: {}, 파라미터: {}, 리턴 타입: {}", method.getName(), method.getParameterCount(), method.getReturnType()));
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
    public void privateFieldAccess() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Object student = clazz.getDeclaredConstructor().newInstance();
        Field nameField = clazz.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(student, "lywin");
        Method nameMethod = clazz.getMethod("getName");

        Field ageField = clazz.getDeclaredField("age");
        ageField.setAccessible(true);
        ageField.setInt(student, 20);
        Method ageMethod = clazz.getMethod("getAge");

        assertThat(nameMethod.invoke(student)).isEqualTo("lywin");
        assertThat(ageMethod.invoke(student)).isEqualTo(20);
    }
}
