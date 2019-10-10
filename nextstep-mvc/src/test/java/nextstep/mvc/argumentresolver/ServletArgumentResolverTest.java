package nextstep.mvc.argumentresolver;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.TestUserController;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ServletArgumentResolverTest {
    private ServletArgumentResolver resolver = new ServletArgumentResolver();

    @Test
    void request_response_session() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        HttpSession session = request.getSession();

        Class clazz = TestUserController.class;
        Method method = getMethod("create_request_response", clazz.getDeclaredMethods());

        ModelAndView mav = (ModelAndView) method.invoke(
                clazz.getDeclaredConstructor().newInstance(),
                resolver.resolve(request, response, method, 0),
                resolver.resolve(request, response, method, 1),
                resolver.resolve(request, response, method, 2));

        assertThat(mav.getObject("request")).isEqualTo(request);
        assertThat(mav.getObject("response")).isEqualTo(response);
        assertThat(mav.getObject("session")).isEqualTo(session);
    }

    private Method getMethod(String name, Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .get();
    }
}