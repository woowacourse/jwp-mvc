package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        // TODO Question 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
        for (Field field : clazz.getDeclaredFields()) {
            logger.debug("field : {}", field);
            logger.debug("field modifier : {}", field.getModifiers());
            logger.debug("field name : {}", field.getName());
            logger.debug("field type : {}", field.getType());
        }
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            logger.debug("constructor : {}", constructor);
            logger.debug("constructor modifier : {}", constructor.getModifiers());
            logger.debug("constructor name : {}", constructor.getName());
            logger.debug("constructor parameter count : {}", constructor.getParameterCount());
            for (Type type : constructor.getGenericParameterTypes()) {
                logger.debug("constructor parameter type : {}", type);
            }
        }
        for (Method method : clazz.getDeclaredMethods()) {
            logger.debug("method : {}", method);
            logger.debug("constructor modifier : {}", method.getModifiers());
            logger.debug("constructor name : {}", method.getName());
            logger.debug("constructor parameter count : {}", method.getParameterCount());
            for (Type type : method.getGenericParameterTypes()) {
                logger.debug("constructor parameter type : {}", type);
            }
            logger.debug("constructor parameter return : {}", method.getReturnType());
            logger.debug("constructor parameter generic return : {}", method.getGenericReturnType());
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
