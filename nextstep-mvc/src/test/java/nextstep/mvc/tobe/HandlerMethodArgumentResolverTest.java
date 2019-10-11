package nextstep.mvc.tobe;

import nextstep.mvc.tobe.test.TestUser;
import nextstep.mvc.tobe.test.TestUserController;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerMethodArgumentResolverTest {
    private static final Logger logger = LoggerFactory.getLogger(HandlerMethodArgumentResolverTest.class);

    private ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    private final Class clazz = TestUserController.class;

    @Test
    void string() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        String userId = "javajigi";
        String password = "password";
        request.addParameter("userId", userId);
        request.addParameter("password", password);

        Method method = getMethod("create_string", clazz.getDeclaredMethods());
        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        Object[] values = new Object[parameterNames.length];
        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = parameterNames[i];
            logger.debug("parameter : {}", parameterName);
            values[i] = request.getParameter(parameterName);
        }

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), values);
        assertThat(mav.getObject("userId")).isEqualTo(userId);
        assertThat(mav.getObject("password")).isEqualTo(password);
    }

    @Test
    void argumentResolver_string() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        String userId = "javajigi";
        String password = "password";
        request.addParameter("userId", userId);
        request.addParameter("password", password);

        Method method = getMethod("create_string", clazz.getDeclaredMethods());
        Handler handler = new HandlerExecution(clazz.getDeclaredConstructor().newInstance(), method);
        Map<String, Object> model = handler.handle(request, response).getModel();
        assertThat(model.get("userId")).isEqualTo(userId);
        assertThat(model.get("password")).isEqualTo(password);
    }

    @Test
    void argumentResolver_int_long() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Long id = 1234L;
        int age = 20;
        request.addParameter("id", String.valueOf(id));
        request.addParameter("age", String.valueOf(age));

        Method method = getMethod("create_int_long", clazz.getDeclaredMethods());
        Handler handler = new HandlerExecution(clazz.getDeclaredConstructor().newInstance(), method);
        Map<String, Object> model = handler.handle(request, response).getModel();
        assertThat(model.get("id")).isEqualTo(id);
        assertThat(model.get("age")).isEqualTo(age);
    }

//    @Test
    void argumentResolver_javabean() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        String id = "1234";
        String password = "password";
        int age = 20;
        TestUser testUser = new TestUser(id, password, age);

        request.addParameter("id", id);
        request.addParameter("password", password);
        request.addParameter("age", String.valueOf(age));

        Method method = getMethod("create_javabean", clazz.getDeclaredMethods());
        Handler handler = new HandlerExecution(clazz.getDeclaredConstructor().newInstance(), method);
        Map<String, Object> model = handler.handle(request, response).getModel();
        assertThat(model.get("user")).isEqualTo(testUser);
    }

    private Method getMethod(String name, Method[] methods) {
        return Arrays.stream(methods)
            .filter(method -> method.getName().equals(name))
            .findFirst()
            .get();
    }
}
