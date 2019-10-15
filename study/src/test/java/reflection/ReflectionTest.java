package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

        Constructor firstConstructor = clazz.getDeclaredConstructor(String.class, String.class, String.class);
        Object[] firstConstructorArgs = {"writer", "title", "content"};
        Question firstConstructorQuestion = (Question) firstConstructor.newInstance(firstConstructorArgs);
        logger.debug("first constructor : {}", firstConstructorQuestion);
        assertEquals(firstConstructorQuestion, new Question("writer", "title", "content"));

        Constructor secondConstructor = clazz.getDeclaredConstructor(long.class, String.class, String.class, String.class, Date.class, int.class);
        Object[] secondConstructorArgs = {1L, "writer", "title", "content", new Date(), 1000};
        Object secondConstructorQuestion = secondConstructor.newInstance(secondConstructorArgs);
        logger.debug("second constructor : {}", secondConstructorQuestion);
        assertEquals(secondConstructorQuestion, new Question(1L, "writer", "title", "content", new Date(), 1000));
    }

    @Test
    public void privateFieldAccess() throws IllegalAccessException, NoSuchFieldException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = new Student();
        Field ageField = clazz.getDeclaredField("age");
        Field nameField = clazz.getDeclaredField("name");

        ageField.setAccessible(true);
        ageField.set(student, 30);
        nameField.setAccessible(true);
        nameField.set(student, "재성");

        assertThat(student.getAge()).isEqualTo(30);
        assertThat(student.getName()).isEqualTo("재성");
    }
}
