package nextstep.mvc.tobe.argumentresolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.TestUser;
import nextstep.mvc.tobe.TestUserController;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerMethodArgumentResolverTest {
    private static final Logger logger = LoggerFactory.getLogger(HandlerMethodArgumentResolverTest.class);

    private ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @Test
    void requestParam_string() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        String userId = "javajigi";
        String password = "password";
        request.addParameter("userId", userId);
        request.addParameter("password", password);

        Class clazz = TestUserController.class;
        Method method = getMethod("create_string", clazz.getDeclaredMethods());

        Object[] values = HandlerMethodArgumentResolverManager.values(method, request, response);

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), values);
        assertThat(mav.getObject("userId")).isEqualTo(userId);
        assertThat(mav.getObject("password")).isEqualTo(password);
    }

    @Test
    void requestParam_wrapper_class() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Long id = 1234L;
        Integer age = 1111;
        request.addParameter("id", String.valueOf(id));
        request.addParameter("age", String.valueOf(age));

        Class clazz = TestUserController.class;
        Method method = getMethod("create_wrapper_class", clazz.getDeclaredMethods());

        Object[] values = HandlerMethodArgumentResolverManager.values(method, request, response);

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), values);
        assertThat(mav.getObject("id")).isEqualTo(id);
        assertThat(mav.getObject("age")).isEqualTo(age);
    }

    @Test
    void requestParam_primitive() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        long id = 1234;
        int age = 1111;
        request.addParameter("id", String.valueOf(id));
        request.addParameter("age", String.valueOf(age));

        Class clazz = TestUserController.class;
        Method method = getMethod("create_int_long", clazz.getDeclaredMethods());

        Object[] values = HandlerMethodArgumentResolverManager.values(method, request, response);

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), values);
        assertThat(mav.getObject("id")).isEqualTo(id);
        assertThat(mav.getObject("age")).isEqualTo(age);
    }

    @Test
    void modelAttribute() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        String userId = "userId";
        String password = "password";
        int age = 27;
        request.addParameter("userId", userId);
        request.addParameter("password", password);
        request.addParameter("age", String.valueOf(age));

        Class clazz = TestUserController.class;
        Method method = getMethod("create_javabean", clazz.getDeclaredMethods());

        Object[] values = HandlerMethodArgumentResolverManager.values(method, request, response);

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), values);
        TestUser testUser = (TestUser) mav.getObject("testUser");
        assertThat(testUser.getUserId()).isEqualTo(userId);
        assertThat(testUser.getPassword()).isEqualTo(password);
        assertThat(testUser.getAge()).isEqualTo(age);
    }

    @Test
    void requestBody() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        String userId = "userId";
        String password = "password";
        int age = 27;
        TestUser user = new TestUser(userId, password, age);
        request.setContent(new ObjectMapper().writeValueAsString(user).getBytes());

        Class clazz = TestUserController.class;
        Method method = getMethod("create_javabean_json", clazz.getDeclaredMethods());

        Object[] values = HandlerMethodArgumentResolverManager.values(method, request, response);

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), values);
        TestUser testUser = (TestUser) mav.getObject("testUser");
        assertThat(testUser.getUserId()).isEqualTo(userId);
        assertThat(testUser.getPassword()).isEqualTo(password);
        assertThat(testUser.getAge()).isEqualTo(age);
    }

    private Method getMethod(String name, Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .get();
    }
}
