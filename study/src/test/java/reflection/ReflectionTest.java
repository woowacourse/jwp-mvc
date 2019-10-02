package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Field[] fields = clazz.getDeclaredFields();
        Constructor[] constructors = clazz.getDeclaredConstructors();
        Method[] methods = clazz.getDeclaredMethods();

        System.out.println("Fields -----");
        Arrays.stream(fields).forEach(System.out::println);
        System.out.println("Constructors ------");
        Arrays.stream(constructors).forEach(System.out::println);
        System.out.println("Methods -----");
        Arrays.stream(methods).forEach(System.out::println);
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
        Class[] params = new Class[]{String.class, String.class, String.class};
        Class[] params2 = new Class[]{long.class, String.class, String.class, String.class, Date.class, int.class};

        Constructor constructor = clazz.getConstructor(params);
        Object[] obj = new Object[]{"a", "b", "c"};
        Question result = (Question) constructor.newInstance(obj);
        logger.debug(result.toString());

        constructor = clazz.getConstructor(params2);
        obj = new Object[]{1L, "a", "b", "c", new Date(), 1};
        result = (Question) constructor.newInstance(obj);
        logger.debug(result.toString());
        // TODO 인자를 가진 생성자를 활용해 인스턴스를 생성한다.
    }

    @Test
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Field name = clazz.getDeclaredField("name");
        Field age = clazz.getDeclaredField("age");


        Student student = clazz.newInstance();
        name.setAccessible(true);
        name.set(student, "BIMO");
        age.setAccessible(true);
        age.set(student, 26);

        assertEquals(student.getName(), "BIMO");
        assertEquals(student.getAge(), 26);

        // TODO Student private field에 값을 저장하고 조회한다.
    }
}
