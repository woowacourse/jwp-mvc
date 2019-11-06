package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reflection.helper.ExpectedConstructor;
import reflection.helper.ExpectedField;
import reflection.helper.ExpectedMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger log = LoggerFactory.getLogger(ReflectionTest.class);

    interface Hello {
    }

    private static final class PrivateStaticFinalClass {
    }

    @Test
    public void clazz_getName() {
        Class<Question> clazz = Question.class;
        assertThat(clazz.getName()).isEqualTo("reflection.Question");

        assertThat(int.class.getName()).isEqualTo("int");
        assertThat(Integer.class.getName()).isEqualTo("java.lang.Integer");
        assertThat(Integer[].class.getName()).isEqualTo("[Ljava.lang.Integer;");

        assertThat(Hello.class.getName()).isEqualTo("reflection.ReflectionTest$Hello");
        assertThat(Hello[].class.getName()).isEqualTo("[Lreflection.ReflectionTest$Hello;");
    }

    @Test
    public void clazz_getModifiers() {
        Class<Question> clazz = Question.class;
        int questionModifiers = clazz.getModifiers();
        assertThat(Modifier.isPublic(questionModifiers)).isTrue();


        int helloModifiers = Hello.class.getModifiers();
        assertThat(Modifier.isInterface(helloModifiers)).isTrue();


        int privateStaticFinalClassModifiers = PrivateStaticFinalClass.class.getModifiers();
        assertThat(Modifier.isPrivate(privateStaticFinalClassModifiers)).isTrue();
        assertThat(Modifier.isStatic(privateStaticFinalClassModifiers)).isTrue();
        assertThat(Modifier.isFinal(privateStaticFinalClassModifiers)).isTrue();
    }

    @Test
    public void clazz_getFields() {
        Class<Question> clazz = Question.class;

        Map<String, ExpectedField> expectedFields = new HashMap<>() {{
            put("HELLO", ExpectedField.builder()
                    .declaringClass(Question.class)
                    .type(String.class)
                    .modifiers(Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL)
                    .name("HELLO")
                    .build());

            put("test1", ExpectedField.builder()
                    .declaringClass(Question.class)
                    .type(int.class)
                    .modifiers(Modifier.PUBLIC)
                    .name("test1")
                    .build());
        }};

        Field[] fields = clazz.getFields();
        assertThat(fields.length).isEqualTo(expectedFields.size());
        for (Field field : fields) {
            log.debug("field name: {}", field.getName());
            ExpectedField expectedField = expectedFields.get(field.getName());

            expectedField.assertField(field);
        }
    }

    @Test
    public void clazz_getFields_getParentField() {
        Class<Child> clazz = Child.class;

        Map<String, ExpectedField> expectedFields = new HashMap<>() {{
            put("age", ExpectedField.builder()
                    .declaringClass(Parent.class)
                    .type(int.class)
                    .modifiers(Modifier.PUBLIC)
                    .name("age")
                    .build());
        }};

        Field[] fields = clazz.getFields();
        assertThat(fields.length).isEqualTo(expectedFields.size());
        for (Field field : fields) {
            log.debug("field name: {}", field.getName());
            ExpectedField expectedField = expectedFields.get(field.getName());

            expectedField.assertField(field);
        }
    }

    class Parent {
        public int age;
    }

    class Child extends Parent {
    }

    @Test
    public void clazz_getDeclaredFields() {
        Class<Simple> clazz = Simple.class;

        Map<String, ExpectedField> expectedFields = new HashMap<>() {{
            put("publicString", ExpectedField.builder()
                    .declaringClass(Simple.class)
                    .type(String.class)
                    .modifiers(Modifier.PUBLIC)
                    .name("publicString")
                    .build());

            put("privateInt", ExpectedField.builder()
                    .declaringClass(Simple.class)
                    .type(int.class)
                    .modifiers(Modifier.PRIVATE)
                    .name("privateInt")
                    .build());
        }};

        Field[] fields = clazz.getDeclaredFields();
        log.debug("fields: {}", Arrays.toString(fields));
        assertThat(fields.length).isEqualTo(expectedFields.size());
        for (Field field : fields) {
            log.debug("field name: {}", field.getName());
            ExpectedField expectedField = expectedFields.get(field.getName());

            expectedField.assertField(field);
        }
    }

    @Test
    public void clazz_getConstructors() {
        Class<Question> clazz = Question.class;

        List<ExpectedConstructor> expectedConstructors = Arrays.asList(
                ExpectedConstructor.builder()
                        .modifiers(Modifier.PUBLIC)
                        .parameterTypes(new Class<?>[]{String.class, String.class, String.class})
                        .build(),
                ExpectedConstructor.builder()
                        .modifiers(Modifier.PUBLIC)
                        .parameterTypes(new Class<?>[]{long.class, String.class, String.class, String.class, Date.class, int.class})
                        .build()
        );

        Constructor<?>[] constructors = clazz.getConstructors();
        assertThat(constructors.length).isEqualTo(expectedConstructors.size());

        long existConstructorCount = Arrays.asList(constructors).stream()
                .filter(constructor -> isIncludedBy(expectedConstructors, constructor))
                .count();
        assertThat(existConstructorCount).isEqualTo(constructors.length);


    }

    @Test
    public void clazz_getDeclaredConstructors() {
        Class<Question> clazz = Question.class;

        List<ExpectedConstructor> expectedConstructors = Arrays.asList(
                ExpectedConstructor.builder()
                        .modifiers(Modifier.PUBLIC)
                        .parameterTypes(new Class<?>[]{String.class, String.class, String.class})
                        .build(),
                ExpectedConstructor.builder()
                        .modifiers(Modifier.PUBLIC)
                        .parameterTypes(new Class<?>[]{long.class, String.class, String.class, String.class, Date.class, int.class})
                        .build(),
                ExpectedConstructor.builder()
                        .modifiers(Modifier.PRIVATE)
                        .parameterTypes(new Class<?>[]{String.class})
                        .build()
        );

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        assertThat(constructors.length).isEqualTo(expectedConstructors.size());

        long existConstructorCount = Arrays.asList(constructors).stream()
                .filter(constructor -> isIncludedBy(expectedConstructors, constructor))
                .count();
        assertThat(existConstructorCount).isEqualTo(constructors.length);
    }

    private boolean isIncludedBy(List<ExpectedConstructor> expectedConstructors, Constructor<?> constructor) {
        return expectedConstructors.stream().anyMatch(expectedConstructor -> expectedConstructor.isSameSignature(constructor));
    }

    @Test
    public void clazz_getDeclaredMethods() {
        Class<Simple> clazz = Simple.class;

        Map<String, ExpectedMethod> expectedMethods = new HashMap<>() {{
            put("toString", ExpectedMethod.builder()
                    .declaringClass(Simple.class)
                    .name("toString")
                    .modifiers(Modifier.PUBLIC)
                    .returnType(String.class)
                    .build());

            put("withArg", ExpectedMethod.builder()
                    .declaringClass(Simple.class)
                    .name("withArg")
                    .modifiers(Modifier.PUBLIC)
                    .parameterTypes(new Class<?>[]{int.class})
                    .returnType(String.class)
                    .build());

            put("withExceptions", ExpectedMethod.builder()
                    .declaringClass(Simple.class)
                    .name("withExceptions")
                    .modifiers(Modifier.PUBLIC)
                    .exceptionTypes(new Class<?>[]{NullPointerException.class, IllegalAccessException.class})
                    .returnType(String.class)
                    .build());
        }};

        Method[] methods = clazz.getDeclaredMethods();
        assertThat(methods.length).isEqualTo(expectedMethods.size());

        for (Method method : methods) {
            log.debug(method.getName());
            ExpectedMethod expectedMethod = expectedMethods.get(method.getName());

            expectedMethod.assertMethod(method);
        }
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();

        List<Object[]> argsList = Arrays.asList(
                new Object[]{1L, "writer", "title", "contents", new Date(), 0},
                new Object[]{"writer", "title", "contents"}
        );
        List<Question> expectedQuestions = Arrays.asList(
                new Question(1L, "writer", "title", "contents", new Date(), 0),
                new Question("writer", "title", "contents")
        );

        for (Constructor constructor : constructors) {
            for (int i = 0; i < argsList.size(); i++) {
                Object[] args = argsList.get(i);
                if (constructor.getParameterCount() != args.length) {
                    continue;
                }
                Question question = (Question) constructor.newInstance(args);
                log.debug("question: {}", question);
                assertThat(question).isEqualTo(expectedQuestions.get(i));
            }
        }
    }

    @Test
    public void privateFieldAccess() throws Exception {
        Class<Student> clazz = Student.class;
        log.debug(clazz.getName());

        Student student = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();
        fields[0].setAccessible(true); // name
        fields[1].setAccessible(true); // age

        String expectedName = "int 최대값이 3 * 10^9 보단 작아서 다행인 학생";
        int expectedAge = 1234567890;

        fields[0].set(student, expectedName);
        fields[1].set(student, expectedAge);

        assertThat(student.getName()).isEqualTo(expectedName);
        assertThat(student.getAge()).isEqualTo(expectedAge);
    }
}
