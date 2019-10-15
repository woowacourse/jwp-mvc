package nextstep.mvc.tobe.argumentResolver;

import nextstep.mvc.MethodParameter;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

class ServletArgumentResolverTest {

    @Test
    void supportsParameter() {
        Reflections reflections = new Reflections("nextstep");
        reflections.get
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MethodParameter methodParameter = new MethodParameter(testMethod,);
    }

    @Test
    void resolveArgument() {
    }

    private void testMethod (HttpServletRequest request , HttpServletResponse response) {
    }
}