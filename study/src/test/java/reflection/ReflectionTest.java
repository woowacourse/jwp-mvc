package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ReflectionTest {
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
    void constructor_with_args() throws Exception {
        final Class<Question> clazz = Question.class;

        // 인자를 가진 생성자를 활용해 인스턴스를 생성한다.
        final Map<Class, Object> classObjectMap = new HashMap<>();
        classObjectMap.put(int.class, 0);
        classObjectMap.put(long.class, 0L);
        classObjectMap.put(char.class, 0);

        final List<Object> list = new ArrayList<>();
        final Constructor constructor = clazz.getConstructors()[0];
        final Class[] parameterTypes = constructor.getParameterTypes();
        for (final Class cls : parameterTypes) {
            list.add(classObjectMap.getOrDefault(cls, null));
        }

        final Question question = (Question) constructor.newInstance(list.toArray());
        logger.debug("test : {}", question.toString());
    }

    @Test
    void privateFieldAccess() throws IllegalAccessException, NoSuchFieldException {
        final Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        // Student private field에 값을 저장하고 조회한다.
        final Student student = new Student();
        setPrivateField(student, "name", "박재성");
        setPrivateField(student, "age", 30);
        logger.debug(student.toString());
    }

    private void setPrivateField(final Object instance, final String fieldName, final Object value)
            throws NoSuchFieldException, IllegalAccessException {
        final Class clazz = instance.getClass();
        final Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }
}
