package nextstep.mvc.tobe.argumentresolver;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;

class ArgumentResolverTest {

    @Test
    void initTest() {
        ArgumentResolver resolver = ArgumentResolver.getInstance();
        assertThat(resolver.getArguments()).isNotEmpty();
    }

    @Test
    void resolveTest() throws NoSuchMethodException {
        HttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        HttpServletResponse response = new MockHttpServletResponse();

        Method method = this.getClass().getDeclaredMethod("httpServletRequestMethod", HttpServletRequest.class);
        Parameter parameter = method.getParameters()[0];

        ArgumentResolver resolver = ArgumentResolver.getInstance();
        Argument argument = resolver.resolveParam(parameter);
        assertThat(argument).isInstanceOf(ServletParamResolver.class);
    }

    void httpServletRequestMethod(HttpServletRequest request) {

    }

}