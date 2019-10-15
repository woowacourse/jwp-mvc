package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.Date;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        getConstructorInformation(clazz);

        logger.debug("");
        getMethodInformation(clazz);

        logger.debug("");
        getFieldInformation(clazz);
    }

    private void getFieldInformation(Class<Question> clazz) {
        logger.debug("Field");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            logger.debug("field : {}", field);
            logger.debug("field name : {}", field.getName());
            logger.debug("field type : {}", field.getType());
            logger.debug("field modifier : {}", Modifier.toString(field.getModifiers()));
            logger.debug("-------------------------------------------");
        }
    }

    private void getMethodInformation(Class<Question> clazz) {
        logger.debug("Method");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            logger.debug("method : {}", method);
        }
    }

    private void getConstructorInformation(Class<Question> clazz) {
        logger.debug("Constructor");

        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor ct : constructors) {
            logger.debug("name : {}", ct.getName());
            logger.debug("declare class : {}", ct.getDeclaringClass());

            Class[] params = ct.getParameterTypes();
            for (Class param : params) {
                logger.debug("param : {}", param);
            }
            logger.debug("-------------------------------------------");
        }
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getDeclaredConstructors();

        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }
        }

        Constructor constructor = clazz.getConstructor(String.class, String.class, String.class);
        Constructor constructor2 = clazz.getConstructor(
                long.class, String.class, String.class, String.class, Date.class, int.class);
        Question question = (Question) constructor.newInstance("iva", "wooteco", "hello");
        Question question2 = (Question) constructor2.newInstance(
                Long.parseLong("1"), "iva", "wooteco", "hello", new Date(), Integer.parseInt("1"));
        logger.debug("question : {}", question.toString());
        logger.debug("question2 : {}", question2.toString());
    }

    @Test
    public void privateFieldAccess()
            throws IllegalAccessException, InvocationTargetException, NoSuchFieldException, NoSuchMethodException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = new Student();
        setField(clazz, student, "name", "harry");
        setField(clazz, student, "age", 26);

        Method[] methods = {clazz.getDeclaredMethod("getName"), clazz.getDeclaredMethod("getAge")};

        for (Method method : methods) {
            Object value = method.invoke(student);
            logger.debug("value:{}", value);
        }
    }

    private void setField(Class<Student> clazz, Object classType, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field age = clazz.getDeclaredField(fieldName);
        age.setAccessible(true);
        age.set(classType, value);
    }
}
