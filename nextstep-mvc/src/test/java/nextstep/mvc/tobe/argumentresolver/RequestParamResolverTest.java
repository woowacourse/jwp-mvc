package nextstep.mvc.tobe.argumentresolver;

import nextstep.web.annotation.RequestParam;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParamResolverTest {

    @Test
    void isMappingTest() throws NoSuchMethodException {
        Argument argument = new RequestParamResolver();
        Method method = this.getClass().getDeclaredMethod("RequestParam", String.class);
        Parameter parameter = method.getParameters()[0];

        assertThat(argument.isMapping(parameter)).isTrue();
    }

    @Test
    void resolveTest() throws NoSuchMethodException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        HttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("name", "van");

        Argument argument = new RequestParamResolver();
        Method method = this.getClass().getDeclaredMethod("RequestParam", String.class);
        Parameter parameter = method.getParameters()[0];

        assertThat(argument.resolve(request, response, parameter)).isEqualTo("van");
    }

    void RequestParam(@RequestParam("name") String name) {

    }
}