package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        logger.debug("Question 클래스의 모든 필드 :");
        for (final Field field : clazz.getDeclaredFields()) {
            logger.debug("{} {} {}",
                    Modifier.toString(field.getModifiers()),
                    field.getType().getSimpleName(),
                    field.getName());
        }

        logger.debug("Question 클래스의 모든 생성자 :");
        for (final Constructor constructor : clazz.getDeclaredConstructors()) {
            logger.debug("{} {}", Modifier.toString(constructor.getModifiers()), constructor.getName());
            for (final Parameter parameter : constructor.getParameters()) {
                logger.debug("{} {} {}",
                        Modifier.toString(parameter.getModifiers()),
                        parameter.getType().getSimpleName(),
                        parameter.toString());
            }
        }

        logger.debug("Question 클래스의 모든 메소드 :");
        for (final Method method : clazz.getDeclaredMethods()) {
            logger.debug("{} {} {}",
                    Modifier.toString(method.getModifiers()),
                    method.getReturnType().getSimpleName(),
                    method.getName());
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
}
