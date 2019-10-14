package nextstep.mvc.argumentresolver;

import nextstep.mvc.handlermapping.HandlerExecution;
import nextstep.mvc.tobe.TestUserController;
import nextstep.mvc.modelandview.ModelAndView;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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

        List<MethodParameter> methodParameters = new HandlerExecution(method, clazz.getDeclaredConstructor().newInstance()).extractMethodParameters();

        ModelAndView mav = (ModelAndView) method.invoke(
                clazz.getDeclaredConstructor().newInstance(),
                resolver.resolve(request, response, methodParameters.get(0)),
                resolver.resolve(request, response, methodParameters.get(1)),
                resolver.resolve(request, response, methodParameters.get(2)));

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
