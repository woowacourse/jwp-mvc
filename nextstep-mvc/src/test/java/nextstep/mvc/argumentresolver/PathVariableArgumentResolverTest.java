package nextstep.mvc.argumentresolver;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.TestUserController;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PathVariableArgumentResolverTest {
    private PathVariableArgumentResolver resolver = new PathVariableArgumentResolver();

    @Test
    void request_param() throws Exception {
        long id = 1L;

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/users/" + id);
        MockHttpServletResponse response = new MockHttpServletResponse();

        Class clazz = TestUserController.class;
        Method method = getMethod("show_pathvariable", clazz.getDeclaredMethods());
        List<MethodParameter> methodParameters = new HandlerExecution(method, clazz.getDeclaredConstructor().newInstance()).extractMethodParameters();

        long resolvedId = (long) resolver.resolve(request, response, methodParameters.get(0));

        assertThat(resolvedId).isEqualTo(id);
    }

    private Method getMethod(String name, Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .get();
    }
}
