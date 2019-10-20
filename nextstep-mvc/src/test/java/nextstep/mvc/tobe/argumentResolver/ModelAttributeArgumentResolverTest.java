package nextstep.mvc.tobe.argumentResolver;

import nextstep.mvc.MethodParameter;
import nextstep.mvc.mock.ArgumentResolverTestHelper;
import nextstep.mvc.mock.StringTestUser;
import nextstep.mvc.mock.TestUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ModelAttributeArgumentResolverTest {
    List<MethodParameter> methodParameters;
    HandlerMethodArgumentResolver argumentResolver;

    @BeforeEach
    void setup() throws NoSuchMethodException {
        Method method = ArgumentResolverTestHelper.class.getDeclaredMethod("modelAttributeArgumentsMethod", StringTestUser.class, HttpServletResponse.class, HttpServletRequest.class);
        methodParameters = Arrays.asList(method.getParameters()).stream()
                .map(parameter -> new MethodParameter(method, parameter))
                .collect(Collectors.toList());
        argumentResolver = new ModelAttributeArgumentResolver();
    }

    @DisplayName("ModelAttribute 어노테이션 서포트 테스트")
    @Test
    void supportsParameter() {
        assertThat(argumentResolver.supportsParameter(methodParameters.get(0))).isTrue();
        assertThat(argumentResolver.supportsParameter(methodParameters.get(1))).isFalse();
        assertThat(argumentResolver.supportsParameter(methodParameters.get(2))).isFalse();
    }

    @Test
    void resolveArgument() {
        methodParameters.stream()
                .filter(parameter -> argumentResolver.supportsParameter(parameter))
                .collect(Collectors.toList());

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        StringTestUser requestUser = new StringTestUser("abc", "123");
        request.setParameter("userId", requestUser.getUserId());
        request.setParameter("password", requestUser.getPassword());

        StringTestUser testUser = (StringTestUser) argumentResolver.resolveArgument(methodParameters.get(0), request, response);
        assertThat(testUser.getUserId()).isEqualTo(requestUser.getUserId());
        assertThat(testUser.getPassword()).isEqualTo(requestUser.getPassword());
    }
}