package nextstep.mvc.tobe.method;

import nextstep.mvc.tobe.TestUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;

class DefaultHandlerMethodArgumentResolverTest {
    private DefaultHandlerMethodArgumentResolver resolver = new DefaultHandlerMethodArgumentResolver();
    private Class clazz = DefaultHandlerMethodArgumentResolverTest.class;
    private Map<Class<?>, Parameter> params;
    private MockHttpServletRequest request;
    private Method method;

    private String id = "1";
    private String age = "20";
    private String userId = "javajigi";
    private String password = "password";
    private String bool = "true";

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        method = clazz.getDeclaredMethod("sampleWrappers", Long.class, Integer.class, String.class);

        params = Stream.of(clazz.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("sample"))
                .map(Executable::getParameters)
                .flatMap(Arrays::stream)
                .collect(Collectors.toMap(Parameter::getType, x -> x));

        request = new MockHttpServletRequest();
        request.addParameter("userId", userId);
        request.addParameter("password", password);
        request.addParameter("id", id);
        request.addParameter("age", age);
        request.addParameter("bool", bool);
    }

    @Test
    void 모든_필드_생성자_javaBean_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(TestUser.class), "testUser", 1);

        // when
        final TestUser actual = (TestUser) resolver.resolveArgument(request, methodParameter, method);

        // then
        assertThat(actual.getUserId()).isEqualTo(userId);
        assertThat(actual.getPassword()).isEqualTo(password);
        assertThat(actual.getAge()).isEqualTo(Long.parseLong(age));
    }

    @Test
    void 기본생성자만_있는_javaBean_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(OnlyDefaultConstructorJavaBean.class), "javaBean", 1);

        // when
        final OnlyDefaultConstructorJavaBean actual = (OnlyDefaultConstructorJavaBean) resolver.resolveArgument(request, methodParameter, method);

        // then
        assertThat(actual.getUserId()).isEqualTo(userId);
        assertThat(actual.getPassword()).isEqualTo(password);
        assertThat(actual.getAge()).isEqualTo(Long.parseLong(age));
    }

    @Test
    void long_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(long.class), "id", 1);

        // when
        final Object actual = resolver.resolveArgument(request, methodParameter, method);

        // then
        assertThat(actual).isEqualTo(Long.parseLong(id));
    }

    @Test
    void int_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(int.class), "age", 1);

        // when
        final Object actual = resolver.resolveArgument(request, methodParameter, method);

        // then
        assertThat(actual).isEqualTo(Integer.parseInt(age));
    }

    @Test
    void boolean_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(boolean.class), "bool", 1);
        // when
        final Object actual = resolver.resolveArgument(request, methodParameter, method);

        // then
        assertThat(actual).isEqualTo(Boolean.parseBoolean(bool));
    }

    @Test
    void Long_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(Long.class), "id", 1);

        // when
        final Object actual = resolver.resolveArgument(request, methodParameter, method);

        // then
        assertThat(actual).isEqualTo(Long.parseLong(id));
    }

    @Test
    void Integer_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(Integer.class), "id", 1);

        // when
        final Object actual = resolver.resolveArgument(request, methodParameter, method);

        // then
        assertThat(actual).isEqualTo(Integer.parseInt(id));
    }

    @Test
    void String_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(String.class), "userId", 1);

        // when
        final Object actual = resolver.resolveArgument(request, methodParameter, method);

        // then
        assertThat(actual).isEqualTo(userId);
    }

    void samplePrimitive(final long id, final int age, final boolean bool) {
    }

    void sampleJavaBean(final TestUser testUser, final OnlyDefaultConstructorJavaBean javaBean) {
    }

    void sampleWrappers(final Long id, final Integer age, final String userId) {
    }
}

class OnlyDefaultConstructorJavaBean {
    private String userId;
    private String password;
    private int age;

    public OnlyDefaultConstructorJavaBean() {
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }
}