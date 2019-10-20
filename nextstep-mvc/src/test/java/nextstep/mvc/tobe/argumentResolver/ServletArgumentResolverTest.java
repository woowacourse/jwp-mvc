package nextstep.mvc.tobe.argumentResolver;

import nextstep.mvc.MethodParameter;
import nextstep.mvc.mock.ArgumentResolverTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ServletArgumentResolverTest {

    @DisplayName("httpServletMethod 서포트 테스트")
    @Test
    void supportsParameter() throws NoSuchMethodException {
        Method method = ArgumentResolverTestHelper.class.getDeclaredMethod("servletArgumentsMethod", HttpServletResponse.class, HttpServletRequest.class);
        List<MethodParameter> methodParameters = Arrays.asList(method.getParameters()).stream()
                .map(x -> new MethodParameter(method, x))
                .collect(Collectors.toList());
        ServletArgumentResolver argumentResolver = new ServletArgumentResolver();
        assertThat(argumentResolver.supportsParameter(methodParameters.get(0))).isTrue();
        assertThat(argumentResolver.supportsParameter(methodParameters.get(1))).isTrue();
    }
}