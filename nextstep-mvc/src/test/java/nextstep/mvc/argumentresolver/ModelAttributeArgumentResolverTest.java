package nextstep.mvc.argumentresolver;

import nextstep.mvc.handlermapping.HandlerExecution;
import nextstep.mvc.tobe.TestUser;
import nextstep.mvc.tobe.TestUserController;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ModelAttributeArgumentResolverTest {
    ModelAttributeArgumentResolver resolver = new ModelAttributeArgumentResolver();

    @Test
    void javabean() throws Exception {
        String userId = "id";
        String password = "password";
        int age = 1;

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addParameter("userId", userId);
        request.addParameter("password", password);
        request.addParameter("age", String.valueOf(age));

        Class clazz = TestUserController.class;
        Method method = getMethod("create_javabean", clazz.getDeclaredMethods());
        List<MethodParameter> methodParameters = new HandlerExecution(method, clazz.getDeclaredConstructor().newInstance()).extractMethodParameters();

        TestUser user = (TestUser) resolver.resolve(request, response, methodParameters.get(0));

        assertThat(user.getAge()).isEqualTo(age);
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getPassword()).isEqualTo(password);
    }

    private Method getMethod(String name, Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .get();
    }
}
