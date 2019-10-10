package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.WebRequest;
import nextstep.mvc.tobe.WebRequestContext;
import org.assertj.core.api.Java6Assertions;
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

class PrimitiveMethodArgumentResolverTest {
    private PrimitiveMethodArgumentResolver resolver = new PrimitiveMethodArgumentResolver();
    private Map<Class<?>, Parameter> params;
    private MockHttpServletRequest request;
    private WebRequest webRequest;
    private Method method;

    private String id = "1";
    private String age = "20";
    private String userId = "javajigi";
    private String password = "password";
    private String bool = "true";

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        method = this.getClass().getDeclaredMethod("sampleWrappers", Long.class, Integer.class, String.class);

        params = Stream.of(this.getClass().getDeclaredMethods())
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

        webRequest = new WebRequestContext(request, null);
    }

    @Test
    void long_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(long.class), "id", 1, method);

        // when
        final Object actual = resolver.resolveArgument(webRequest, methodParameter);

        // then
        Java6Assertions.assertThat(actual).isEqualTo(Long.parseLong(id));
    }

    @Test
    void int_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(int.class), "age", 1, method);

        // when
        final Object actual = resolver.resolveArgument(webRequest, methodParameter);

        // then
        Java6Assertions.assertThat(actual).isEqualTo(Integer.parseInt(age));
    }

    @Test
    void boolean_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(boolean.class), "bool", 1, method);
        // when
        final Object actual = resolver.resolveArgument(webRequest, methodParameter);

        // then
        Java6Assertions.assertThat(actual).isEqualTo(Boolean.parseBoolean(bool));
    }

    @Test
    void Long_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(Long.class), "id", 1, method);

        // when
        final Object actual = resolver.resolveArgument(webRequest, methodParameter);

        // then
        Java6Assertions.assertThat(actual).isEqualTo(Long.parseLong(id));
    }

    @Test
    void Integer_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(Integer.class), "id", 1, method);

        // when
        final Object actual = resolver.resolveArgument(webRequest, methodParameter);

        // then
        Java6Assertions.assertThat(actual).isEqualTo(Integer.parseInt(id));
    }

    @Test
    void String_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(String.class), "userId", 1, method);

        // when
        final Object actual = resolver.resolveArgument(webRequest, methodParameter);

        // then
        Java6Assertions.assertThat(actual).isEqualTo(userId);
    }

    void samplePrimitive(final long id, final int age, final boolean bool) {
    }


    void sampleWrappers(final Long id, final Integer age, final String userId) {
    }
}