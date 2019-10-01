package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        // TODO Question 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
        // 일단 리플렉션 테스트로 리플렉션을 학습해보겠습니다
        logger.debug("##### {} Fields #####", clazz.getName());
        for (Field declaredField : clazz.getDeclaredFields()) {
            logger.debug("Type : Name {}", String.format("%s : %s", declaredField.getType(), declaredField.getName()));
        }
        logger.debug("\r\n");

        logger.debug("##### {} Constructor #####", clazz.getName());
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            logger.debug("Modifiers : {}", String.format("%s ", constructor.getModifiers()));
            for (Parameter parameter : constructor.getParameters()) {
                logger.debug("Params : {}", String.format("Type : %s, Name : %s", parameter.getType(), parameter.getName()));
            }
        }
        logger.debug("\r\n");

        logger.debug("##### {} Method #####", clazz.getName());
        for (Method method : clazz.getDeclaredMethods()) {
            logger.debug(String.format("%s %s %s", method.getModifiers(), method.getReturnType(), method.getName()));
            for (Parameter parameter : method.getParameters()) {
                logger.debug("Params : {}", String.format("Type : %s, Name : %s", parameter.getType(), parameter.getName()));
            }
        }
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("parameter length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }
        }

        // TODO 인자를 가진 생성자를 활용해 인스턴스를 생성한다.
    }

    @Test
    public void privateFieldAccess() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, NoSuchFieldException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = clazz.getConstructor().newInstance();

        Field field = clazz.getDeclaredField("age");
        field.setAccessible(true);
        field.setInt(student, 29);

        field = clazz.getDeclaredField("name");
        field.setAccessible(true);
        field.set(student, "sloth");

        assertThat(student.getName()).isEqualTo("sloth");
        assertThat(student.getAge()).isEqualTo(29);
    }
}
