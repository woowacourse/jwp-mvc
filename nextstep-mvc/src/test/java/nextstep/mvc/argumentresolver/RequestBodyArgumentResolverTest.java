package nextstep.mvc.argumentresolver;

import nextstep.mvc.handlermapping.HandlerExecution;
import nextstep.mvc.tobe.TestUser;
import nextstep.mvc.tobe.TestUserController;
import nextstep.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestBodyArgumentResolverTest {
    private ArgumentResolver resolver = new RequestBodyArgumentResolver();

    @Test
    void request_body() throws Exception {
        TestUser user = new TestUser("kjm", "kjm", 10);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setContent(JsonUtils.toJson(user).getBytes());

        Class clazz = TestUserController.class;
        Method method = getMethod("request_body", clazz.getDeclaredMethods());
        List<MethodParameter> methodParameters = new HandlerExecution(method, clazz.getDeclaredConstructor().newInstance()).extractMethodParameters();

        TestUser resolvedUser = (TestUser) resolver.resolve(request, response, methodParameters.get(0));

        assertThat(user.getAge()).isEqualTo(resolvedUser.getAge());
        assertThat(user.getUserId()).isEqualTo(resolvedUser.getUserId());
        assertThat(user.getPassword()).isEqualTo(resolvedUser.getPassword());
    }

    private Method getMethod(String name, Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .get();
    }
}
