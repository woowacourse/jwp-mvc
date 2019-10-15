package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.TestUser;
import nextstep.mvc.tobe.TestUserController;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class ModelAttributeArgumentResolverTest extends AbstractArgumentResolverTest {
    private ModelAttributeArgumentResolver resolver = new ModelAttributeArgumentResolver();

    @Test
    void javabean() {
        String userId = "id";
        String password = "password";
        int age = 1;

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addParameter("userId", userId);
        request.addParameter("password", password);
        request.addParameter("age", String.valueOf(age));

        Class<TestUserController> clazz = TestUserController.class;
        Method method = getMethod("create_javabean", clazz.getDeclaredMethods());
        MethodParameter testUserParam = getNthMethodParam(method, 0);
        TestUser user = (TestUser) resolver.resolve(request, response, testUserParam);

        assertThat(user.getAge()).isEqualTo(age);
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getPassword()).isEqualTo(password);
    }
}