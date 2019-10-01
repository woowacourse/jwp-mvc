package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        // TODO Question 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.

        Class<Question> clazz = Question.class;
        logger.debug("클래스 명 : {}", clazz.getSimpleName());

        for (Constructor<?> constructor : clazz.getConstructors()) {
            String joinedParams = parseParameters(constructor.getParameters());
            logger.debug("생성자 정보 : {}({})", clazz.getSimpleName(), joinedParams);
        }

        for(Field field : clazz.getDeclaredFields()) {
            logger.debug("필드 정보 : {} {} {}", Modifier.toString(field.getModifiers()),field.getType().getSimpleName(), field.getName());
        }

        for (Method method : clazz.getDeclaredMethods()) {
            String joinedParams = parseParameters(method.getParameters());
            logger.debug("메서드 정보 : {} {} {}({})",Modifier.toString(method.getModifiers()), method.getReturnType().getSimpleName(), method.getName(), joinedParams);
        }
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

    private String parseParameters(Parameter[] parameters) {
        List<String> lists = new ArrayList<>();
        Arrays.stream(parameters)
            .forEach((param) -> lists.add(param.getType().getSimpleName()));
        return String.join(", ", lists);
    }
}
