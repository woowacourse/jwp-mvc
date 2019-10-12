package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            logger.debug("필드 이름 : {}, 접근 제어자 : {}", field.getName(), field.getModifiers());
        }

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            logger.debug("생성자 이름 : {}, 접근 제어자 : {}, 파라미터 타입 : {}", constructor.getName(), constructor.getModifiers(), constructor.getParameterTypes());
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            logger.debug("메서드 이름 : {}, 접근 제어자 : {}, 파라미터 타입 : {}", method.getName(), method.getModifiers(), method.getParameterTypes());
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

            if (constructor.getParameterCount() == 3) {
                Question question1 = (Question) constructor.newInstance("재성", "Next Step", "웹 프로그래밍");
                logger.debug("question1 : {}", question1);
            }

            if (constructor.getParameterCount() == 6) {
                Question question2 = (Question) constructor.newInstance(1L, "재성", "Next Step", "웹 프로그래밍", new Date(), 1);
                logger.debug("question2 : {}", question2);
            }
        }

    }

    @Test
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Field name = clazz.getDeclaredField("name");
        Field age = clazz.getDeclaredField("age");

        name.setAccessible(true);
        age.setAccessible(true);

        Student student = new Student();
        name.set(student, "재성");
        age.set(student, 40);

        logger.debug("이름 : {}, 나이 : {}", student.getName(), student.getAge());
    }
}
