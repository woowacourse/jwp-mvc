package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() throws NoSuchMethodException {
        Class<Question> clazz = Question.class;
        logger.debug("class.Name : {}", clazz.getName());
        Arrays.asList(clazz.getDeclaredFields()).forEach(value -> logger.debug("class.ALL_field : {}", value));
        Arrays.asList(clazz.getFields()).forEach(value -> logger.debug("class.public_Fields: {}", value));
        Arrays.asList(clazz.getDeclaredConstructors()).forEach(value -> logger.debug("class.ALL_constructors : {}", value));
        Arrays.asList(clazz.getConstructors()).forEach(value -> logger.debug("class.constructors : {}", value));
        Arrays.asList(clazz.getDeclaredMethods()).forEach(value -> logger.debug("class.ALL_methods: {}", value));
        Arrays.asList(clazz.getMethods()).forEach(value -> logger.debug("class.methods: {}", value));
        // TODO Question 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        Map<Class, Object> data = new HashMap<>();
        data.put(long.class, 29L);
        data.put(String.class, "지노");
        data.put(int.class, 28);
        data.put(Date.class, new Date());

        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            List<Object> params = new ArrayList();
            for (Class paramType : parameterTypes) {
                params.add(data.get(paramType));
                logger.debug("param type : {}", paramType);
            }
            Question question = (Question) constructor.newInstance(params.toArray());
            logger.debug("questionID: {}", question.getQuestionId());
            logger.debug("writer: {}", question.getWriter());
            logger.debug("title: {}", question.getTitle());
            logger.debug("contents: {}", question.getContents());
            logger.debug("createdDate: {}", question.getCreatedDate());
            logger.debug("CountOfComment: {}", question.getCountOfComment());
        }

        // TODO 인자를 가진 생성자를 활용해 인스턴스를 생성한다.
    }

    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAccessible())
                .collect(Collectors.toList());

        fields.get(0).setAccessible(true);
        fields.get(1).setAccessible(true);

        Student student = new Student();
        try {
            fields.get(0).set(student, "test");
            fields.get(1).set(student, 1234);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        logger.debug("{},{}", student.getName(), student.getAge());

        assertThat(student.getName()).isEqualTo("test");
        assertThat(student.getAge()).isEqualTo(1234);


        // TODO Student private field에 값을 저장하고 조회한다.
    }
}
