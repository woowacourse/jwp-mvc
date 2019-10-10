package nextstep.mvc.tobe;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.mock.web.MockHttpServletRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerMethodArgumentResolverTest {
    private static final Logger logger = LoggerFactory.getLogger(HandlerMethodArgumentResolverTest.class);

    private ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @Test
    void string() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        String userId = "javajigi";
        String password = "password";
        request.addParameter("userId", userId);
        request.addParameter("password", password);

        Class clazz = TestUserController.class;
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
    void int_long() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        long id = 1L;
        int age = 2;
        request.addParameter("id", String.valueOf(id));
        request.addParameter("age", String.valueOf(age));

        Class clazz = TestUserController.class;
        Method method = getMethod("create_int_long", clazz.getDeclaredMethods());
        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] values = new Object[parameterNames.length];

        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = parameterNames[i];
            Class<?> paramType = paramTypes[i];

            logger.debug("parameter : {} / {}", parameterName, paramType.getName());
            if (paramType.equals(long.class)) {
                values[i] = Long.parseLong(request.getParameter(parameterName));
                continue;
            }
            if (paramType.equals(int.class)) {
                values[i] = Integer.parseInt(request.getParameter(parameterName));
                continue;
            }
        }

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), values);
        assertThat(mav.getObject("id")).isEqualTo(id);
        assertThat(mav.getObject("age")).isEqualTo(age);
    }

    @Test
    void java_bean() throws Exception{
        String userId = "id";
        String password = "password";
        int age = 1;

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("userId", userId);
        request.addParameter("password", password);
        request.addParameter("age", String.valueOf(age));
        Class clazz = TestUserController.class;
        Method method = getMethod("create_javabean", clazz.getDeclaredMethods());

        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] values = new Object[parameterNames.length];

        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = parameterNames[i];
            Class<?> paramType = paramTypes[i];

            logger.debug("parameter : {} / {}", parameterName, paramType.getName());
            if (paramType.isPrimitive()) {
                if (paramType == long.class) {
                    values[i] = Long.parseLong(request.getParameter(parameterName));
                    continue;
                }
                if (paramType == int.class) {
                    values[i] = Integer.parseInt(request.getParameter(parameterName));
                    continue;
                }
            }

            Constructor<?> constructor = paramType.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();

            for (Field field : paramType.getDeclaredFields()) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                if (fieldType.isPrimitive()) {
                    if (fieldType == long.class) {
                        field.set(instance, Long.parseLong(request.getParameter(field.getName())));
                        continue;
                    }
                    if (fieldType == int.class) {
                        field.set(instance, Integer.parseInt(request.getParameter(field.getName())));
                        continue;
                    }
                }

                if (fieldType == String.class) {
                    field.set(instance, request.getParameter(field.getName()));
                }
            }
            values[i] = instance;
        }

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), values);
        TestUser testUser = (TestUser) mav.getObject("testUser");
        assertThat(testUser.getAge()).isEqualTo(age);
        assertThat(testUser.getPassword()).isEqualTo(password);
        assertThat(testUser.getUserId()).isEqualTo(userId);
    }

    private Method getMethod(String name, Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .get();
    }
}
