package nextstep.mvc.tobe.argumentresolver;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;

class ServletParamResolverTest {
    Argument argument = new ServletParamResolver();

    @Test
    void isMappingTest() throws NoSuchMethodException {
        Method method = this.getClass().getDeclaredMethod("httpServletRequestMethod", HttpServletRequest.class);
        Parameter parameter = method.getParameters()[0];
        assertThat(argument.isMapping(parameter)).isTrue();
    }

    @Test
    void notMappingTest() throws NoSuchMethodException {
        Method method = this.getClass().getDeclaredMethod("voidMethod", int.class);
        Parameter parameter = method.getParameters()[0];
        assertThat(argument.isMapping(parameter)).isFalse();
    }

    @Test
    void resolveTest() throws NoSuchMethodException {
        HttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        HttpServletResponse response = new MockHttpServletResponse();

        Method method = this.getClass().getDeclaredMethod("httpServletRequestMethod", HttpServletRequest.class);
        Parameter parameter = method.getParameters()[0];
        assertThat(argument.resolve(request, response, parameter)).isEqualTo(request);
    }

    void httpServletRequestMethod(HttpServletRequest request) {

    }

    void voidMethod(int i) {

    }
}