package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.TestUserController;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParamArgumentResolverTest extends AbstractArgumentResolverTest {
    private RequestParamArgumentResolver resolver = new RequestParamArgumentResolver();

    @Test
    void request_param_string_and_int() throws Exception {
        String userId = "id";
        String password = "password";
        int age = 1;

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addParameter("userId", userId);
        request.addParameter("password", password);
        request.addParameter("age", String.valueOf(age));

        Class<TestUserController> clazz = TestUserController.class;
        Method method = getMethod("create_request_param", clazz.getDeclaredMethods());
        MethodParameter idParam = getNthMethodParam(method, 0);
        MethodParameter pwdParam = getNthMethodParam(method, 1);
        MethodParameter ageParam = getNthMethodParam(method, 2);

        String resolvedId = (String) resolver.resolve(request, response, idParam);
        String resolvedPW = (String) resolver.resolve(request, response, pwdParam);
        int resolvedAge = (int) resolver.resolve(request, response, ageParam);

        assertThat(resolvedAge).isEqualTo(age);
        assertThat(resolvedId).isEqualTo(userId);
        assertThat(resolvedPW).isEqualTo(password);
    }

    @Test
    void create_int_long_기본값_확인() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Class<TestUserController> clazz = TestUserController.class;
        Method method = getMethod("create_int_long", clazz.getDeclaredMethods());
        MethodParameter idParam = getNthMethodParam(method, 0);
        MethodParameter pwdParam = getNthMethodParam(method, 1);

        long id = (long) resolver.resolve(request, response, idParam);
        int age = (int) resolver.resolve(request, response, pwdParam);

        assertThat(id).isEqualTo(0L);
        assertThat(age).isEqualTo(0);
    }
}