package nextstep.mvc.tobe.resolver;

import nextstep.web.annotation.PathVariable;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
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
        final MethodParameter methodParameter = new MethodParameter(parameters[0], "name", 1, method);

        // when & then
        assertThat(resolver.supports(methodParameter)).isTrue();
    }

    @Test
    void supports_PathVariable_아닌_경우_false() {
        // given
        final MethodParameter methodParameter = new MethodParameter(parameters[1], "name", 1, method);

        // when & then
        assertThat(resolver.supports(methodParameter)).isFalse();
    }

    @Test
    void resolveArgument_추출_성공() {
        // given
        final MethodParameter methodParameter = new MethodParameter(parameters[0], "id", 1,method);
        request.setRequestURI("/users/10");

        // when
        final Object actual = resolver.resolveArgument(request, methodParameter);

        // then
        assertThat(actual).isEqualTo(10L);
    }

    @Test
    void pathVariable_이_두개일_경우() {
        // given
        final Method method = Stream.of(clazz.getDeclaredMethods())
                .filter(x -> x.getName().equals("foo2"))
                .findAny()
                .get();
        final Parameter[] parameters = method.getParameters();

        final MethodParameter methodParameter = new MethodParameter(parameters[0], "id", 1,method);
        final MethodParameter methodParameter2 = new MethodParameter(parameters[1], "userId", 1,method);
        request.setRequestURI("/users/10/12");

        // when
        final Object id = resolver.resolveArgument(request, methodParameter);
        final Object userId = resolver.resolveArgument(request, methodParameter2);

        // then
        assertThat(id).isEqualTo(10L);
        assertThat(userId).isEqualTo(12L);
    }

    @RequestMapping(value = "/users/{id}")
    Long foo(@PathVariable Long args1, String args2) {
        return args1;
    }

    @RequestMapping(value = "/users/{id}/{userId}", method = RequestMethod.GET)
    public long[] foo2(@PathVariable long id, @PathVariable long userId) {
        return new long[]{id, userId};
    }
}
