package reflection;

import annotation.Array;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        Method[] methods = clazz.getDeclaredMethods();
        Constructor[] constructors = clazz.getDeclaredConstructors();
        Field[] fields = clazz.getDeclaredFields();

        Arrays.asList(methods).forEach(x -> logger.debug(x.getName()));
        assertThat(methods.length).isEqualTo(11);

        Arrays.asList(constructors).forEach(x -> logger.debug("{}: {}", x, x.getParameterTypes().toString()));
        assertThat(constructors.length).isEqualTo(2);

        Arrays.asList(fields).forEach(x -> logger.debug(x.getName()));
        assertThat(fields.length).isEqualTo(6);
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getDeclaredConstructors();
        List<Class[]> parameterTypeList = new ArrayList<>();

        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            parameterTypeList.add(parameterTypes);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }
        }

        Constructor constructor = clazz.getDeclaredConstructor(parameterTypeList.get(0));
        Question question = (Question) constructor.newInstance("a", "b", "c");

        Constructor constructor2 = clazz.getDeclaredConstructor(parameterTypeList.get(1));
        Question question2 = (Question) constructor2.newInstance(5l, "a", "b", "c", new Date(), 20);

        assertThat(question).isEqualTo(new Question("a", "b", "c"));
        assertThat(question2).isEqualTo(new Question(5l, "a", "b", "c", new Date(), 20));
    }

    @Test
    public void privateFieldAccess() throws IllegalAccessException, NoSuchFieldException {
        final int dummyAge = 29;
        final String dummyName = "규동";

        Student student = new Student();

        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Field nameField = clazz.getDeclaredField("name");
        Field ageField = clazz.getDeclaredField("age");

        nameField.setAccessible(true);
        ageField.setAccessible(true);
        nameField.set(student, dummyName);
        ageField.set(student, dummyAge);

        assertThat(student.getName()).isEqualTo(dummyName);
        assertThat(student.getAge()).isEqualTo(dummyAge);
    }

    @Test
    public void getClassByMethod() throws NoSuchMethodException {
        Class<Question> clazz = Question.class;
        Method method = clazz.getDeclaredMethod("getWriter");
        assertThat(method.getDeclaringClass()).isEqualTo(clazz);
    }

    @Test
    public void getMethodArray() throws NoSuchMethodException {
        Class clazz = Question.class;
        Method method = clazz.getDeclaredMethod("getTitle");
        Array array = method.getAnnotation(Array.class);
        System.out.println(array.name()[0]);
    }
}
