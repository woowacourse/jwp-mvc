package nextstep.mvc.argumentresolver;

import nextstep.mvc.tobe.TestUserController;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParamArgumentResolverTest {
    private RequestParamArgumentResolver resolver = new RequestParamArgumentResolver();

    @Test
    void request_param() throws Exception {
        String userId = "id";
        String password = "password";
        int age = 1;

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addParameter("userId", userId);
        request.addParameter("password", password);
        request.addParameter("age", String.valueOf(age));

        Class clazz = TestUserController.class;
        Method method = getMethod("create_request_param", clazz.getDeclaredMethods());

        String resolvedId = (String) resolver.resolve(request, response, method, 0);
        String resolvedPW = (String) resolver.resolve(request, response, method, 1);
        int resolvedAge = (int) resolver.resolve(request, response, method, 2);

        assertThat(resolvedAge).isEqualTo(age);
        assertThat(resolvedId).isEqualTo(userId);
        assertThat(resolvedPW).isEqualTo(password);
    }

    private Method getMethod(String name, Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .get();
    }
}