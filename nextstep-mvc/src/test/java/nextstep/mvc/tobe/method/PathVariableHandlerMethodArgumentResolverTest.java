package nextstep.mvc.tobe.method;

import nextstep.web.annotation.PathVariable;
import nextstep.web.annotation.RequestMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PathVariableHandlerMethodArgumentResolverTest {
    private PathVariableHandlerMethodArgumentResolver resolver = new PathVariableHandlerMethodArgumentResolver();
    private Class clazz = PathVariableHandlerMethodArgumentResolverTest.class;
    private Parameter[] parameters;
    private MockHttpServletRequest request;
    private String id = "1";
    private Method method;

    @BeforeEach
    void setUp() {
        method = Stream.of(clazz.getDeclaredMethods())
                .filter(method -> method.getName().equals("foo"))
                .findAny()
                .get();
        parameters = method.getParameters();

        request = new MockHttpServletRequest();
        request.addParameter("id", id);
    }

    @Test
    void supports_PathVariable_일_경우_true() {
        // given
        final MethodParameter methodParameter = new MethodParameter(parameters[0], "name", 1);

        // when & then
        assertThat(resolver.supports(methodParameter)).isTrue();
    }

    @Test
    void supports_PathVariable_아닌_경우_false() {
        // given
        final MethodParameter methodParameter = new MethodParameter(parameters[1], "name", 1);

        // when & then
        assertThat(resolver.supports(methodParameter)).isFalse();
    }

    @Test
    void resolveArgument_추출_성공() {
        // given
        final MethodParameter methodParameter = new MethodParameter(parameters[0], "id", 1);
        request.setRequestURI("/users/10");

        // when
        final Object actual = resolver.resolveArgument(request, methodParameter, method);

        // then
        assertThat(actual).isEqualTo(10L);
    }

    @RequestMapping(value = "/users/{id}")
    Long foo(@PathVariable Long args1, String args2) {
        return args1;
    }
}
