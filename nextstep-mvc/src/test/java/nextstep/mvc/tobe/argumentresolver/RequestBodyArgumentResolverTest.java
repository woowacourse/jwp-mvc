package nextstep.mvc.tobe.argumentresolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import nextstep.mvc.tobe.TestUser;
import nextstep.mvc.tobe.TestUserController;
import nextstep.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyArgumentResolverTest extends AbstractArgumentResolverTest {
    private RequestBodyArgumentResolver resolver = new RequestBodyArgumentResolver();

    @Test
    void requestBody_json_to_object() throws JsonProcessingException {
        TestUser requestUser = new TestUser("id", "pwd", 1);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setContent(JsonUtils.toJson(requestUser).getBytes());

        Class<TestUserController> clazz = TestUserController.class;
        Method method = getMethod("create_requestBody", clazz.getDeclaredMethods());
        MethodParameter testUserParam = getNthMethodParam(method, 0);
        TestUser resolvedUser = (TestUser) resolver.resolve(request, response, testUserParam);

        assertThat(resolvedUser.getAge()).isEqualTo(requestUser.getAge());
        assertThat(resolvedUser.getUserId()).isEqualTo(requestUser.getUserId());
        assertThat(resolvedUser.getPassword()).isEqualTo(requestUser.getPassword());
    }
}