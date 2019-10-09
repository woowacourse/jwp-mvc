package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    private static final String NAME_FIELD = "name";
    private static final String AGE_FIELD = "age";

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        // TODO Question 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
        loggingMetaDataOfClass(clazz, clazz.getFields());
        loggingMetaDataOfClass(clazz, clazz.getConstructors());
        loggingMetaDataOfClass(clazz, clazz.getMethods());
    }

    private void loggingMetaDataOfClass(Class<?> clazz, Object metaData) {
        Class<?> metaDataClass = metaData.getClass();
        logger.debug("{}의 {} 정보: {}", clazz.getName(), metaDataClass, metaData);
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        Constructor selected = null;
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == 3) {
                selected = constructor;
            }
        }

        // TODO 인자를 가진 생성자를 활용해 인스턴스를 생성한다.
        String testWriter = "pkch";
        String testTitle = "Frank Lampard";
        String testContents = "첼시 화이팅!";

        Question createdStudent = (Question) selected.newInstance(testWriter, testTitle, testContents);
        assertThat(createdStudent.getWriter()).isEqualTo(testWriter);
        assertThat(createdStudent.getTitle()).isEqualTo(testTitle);
        assertThat(createdStudent.getContents()).isEqualTo(testContents);
    }

    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        // TODO Student private field에 값을 저장하고 조회한다.
        Student student = new Student();

        String testName = "pkch";
        int testAge = 27;
        try {
            Field name = clazz.getDeclaredField(NAME_FIELD);
            name.setAccessible(true);
            name.set(student, testName);

            Field age = clazz.getDeclaredField(AGE_FIELD);
            age.setAccessible(true);
            age.set(student, testAge);
        } catch (NoSuchFieldException e) {
            logger.error("정의 된 필드가 아닙니다! {}", e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("값을 세팅할 수 없습니다. {}", e.getMessage());
        }

        assertThat(student.getName()).isEqualTo(testName);
        assertThat(student.getAge()).isEqualTo(testAge);
    }
}
