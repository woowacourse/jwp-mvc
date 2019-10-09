package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.in;

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
        // TODO 인자를 가진 생성자를 활용해 인스턴스를 생성한다.

        Class<Question> clazz = Question.class;

        Constructor constructor = clazz.getDeclaredConstructor(String.class, String.class, String.class);
        Question question = (Question) constructor.newInstance("무민", "개발은 어려워", "ㅠㅠ");
        assertThat(question.getWriter()).isEqualTo("무민");
        assertThat(question.getTitle()).isEqualTo("개발은 어려워");
        assertThat(question.getContents()).isEqualTo("ㅠㅠ");
    }

    @Test
    public void privateFieldAccess() throws Exception {
        // TODO Student private field에 값을 저장하고 조회한다.

        Class<Student> clazz = Student.class;
        Student instance = new Student();

        setValue(clazz, instance, "age", 17);
        setValue(clazz, instance, "name", "updatedName");

        assertThat(instance.getAge()).isEqualTo(17);
        assertThat(instance.getName()).isEqualTo("updatedName");
    }

    private void setValue(Class<?> clazz, Object instance, String fieldName ,Object value) throws NoSuchFieldException, IllegalAccessException {
        Field nameField = clazz.getDeclaredField(fieldName);
        nameField.setAccessible(true);
        nameField.set(instance, value);
    }

    private String parseParameters(Parameter[] parameters) {
        List<String> lists = new ArrayList<>();
        Arrays.stream(parameters)
            .forEach((param) -> lists.add(param.getType().getSimpleName()));
        return String.join(", ", lists);
    }
}
