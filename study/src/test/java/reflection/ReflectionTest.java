package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

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
        // TODO Question 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
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
