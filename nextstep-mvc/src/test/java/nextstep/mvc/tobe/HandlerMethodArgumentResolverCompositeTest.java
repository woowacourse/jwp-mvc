package nextstep.mvc.tobe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerMethodArgumentResolverCompositeTest {
    private HandlerMethodArgumentResolver resolver = new HandlerMethodArgumentResolverComposite();
    private MockHttpServletRequest request;
    private Class clazz = TestUserController.class;

    private String id = "1";
    private String age = "20";
    private String userId = "javajigi";
    private String password = "password";

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        request.addParameter("userId", userId);
        request.addParameter("password", password);
        request.addParameter("id", id);
        request.addParameter("age", age);
    }

    @Test
    void create_string() throws Exception {
        // given
        final Method method = getMethod("create_string", clazz.getMethods());

        // when
        final ModelAndView mav = invokeMethod(method);

        // then
        assertThat(mav.getObject("userId")).isEqualTo(userId);
        assertThat(mav.getObject("password")).isEqualTo(password);
    }

    @Test
    void create_int_long() throws Exception {
        // given
        final Method method = getMethod("create_int_long", clazz.getMethods());

        // when
        final ModelAndView mav = invokeMethod(method);

        // then
        assertThat(mav.getObject("id")).isEqualTo(Long.parseLong(id));
        assertThat(mav.getObject("age")).isEqualTo(Integer.parseInt(age));
    }

    @Test
    void create_javabean() throws Exception {
        // given
        final Method method = getMethod("create_javabean", clazz.getMethods());

        // when
        final ModelAndView mav = invokeMethod(method);
        final TestUser testUser = (TestUser) mav.getObject("testUser");

        // then
        assertThat(testUser.getAge()).isEqualTo(Integer.parseInt(age));
        assertThat(testUser.getPassword()).isEqualTo(password);
        assertThat(testUser.getUserId()).isEqualTo(userId);
    }

    @Test
    void show_pathvariable() throws Exception {
        // given
        final Method method = getMethod("show_pathvariable", clazz.getMethods());
        request.setRequestURI("/users/10");

        // when
        final ModelAndView mav = invokeMethod(method);

        // then
        assertThat(mav.getObject("id")).isEqualTo(10L);
    }

    private ModelAndView invokeMethod(final Method method) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final MethodParameters methodParameters = new MethodParameters(method);

        final Object[] values = methodParameters.getMethodParams()
                .stream()
                .map(methodParameter -> resolver.resolveArgument(request, methodParameter))
                .toArray();
        return (ModelAndView) method.invoke(clazz.newInstance(), values);
    }

    private Method getMethod(String name, Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .get();
    }
}