package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
        Arrays.stream(clazz.getDeclaredFields())
                .forEach(r -> logger.debug(r.toString()));
        logger.debug("-------------------------");
        Arrays.stream(clazz.getConstructors())
                .forEach(r -> logger.debug(r.toString()));
        logger.debug("-------------------------");
        Arrays.stream(clazz.getMethods())
                .forEach(r -> logger.debug(r.toString()));
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        long questionId = 1L;
        String writer = "글쓴이";
        String title = "제목";
        String contents = "글내용";
        Date createdDate = new Date();
        int countOfComment = 5;

        Object[] parameters1 = {writer, title, contents};
        Object[] parameters2 = {questionId, writer, title, contents, createdDate, countOfComment};

        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            if (parameterTypes.length == 3) {
                logger.debug(constructor.newInstance(parameters1).toString());
                return;
            }
            logger.debug(constructor.newInstance(parameters2).toString());
        }
    }

    @Test
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
        Student student = clazz.newInstance();
        Field name = clazz.getDeclaredField("name");
        Field age = clazz.getDeclaredField("age");

        name.setAccessible(true);
        age.setAccessible(true);
        name.set(student, "박재성");
        age.set(student, 47);

        logger.debug(student.getName());
        logger.debug("{}", student.getAge());
    }
}
