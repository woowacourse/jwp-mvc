package nextstep.mvc.tobe;

import nextstep.web.annotation.PathVariable;
import nextstep.web.annotation.RequestMapping;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.http.server.PathContainer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerMethodArgumentResolverTest {
    private static final Logger logger = LoggerFactory.getLogger(HandlerMethodArgumentResolverTest.class);

    private static final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    private static final Class clazz = TestUserController.class;

    @Test
    void string() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        String userId = "javajigi";
        String password = "password";
        request.addParameter("userId", userId);
        request.addParameter("password", password);

        Method method = getMethod("create_string", clazz.getDeclaredMethods());

        Class[] parameterTypes = method.getParameterTypes();
        String[] parameterNames = nameDiscoverer.getParameterNames(method);

        Object[] values = new Object[parameterNames.length];
        for (int i = 0; i < parameterNames.length; i++) {
            values[i] = parseParameter(request, parameterTypes[i], parameterNames[i]);
        }

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), values);
        assertThat(mav.getObject("userId")).isEqualTo(userId);
        assertThat(mav.getObject("password")).isEqualTo(password);
    }

    @Test
    void long_int() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        long id = 5l;
        int age = 25;
        request.addParameter("id", String.valueOf(id));
        request.addParameter("age", String.valueOf(age));

        Method method = getMethod("create_int_long", clazz.getDeclaredMethods());

        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] parameterNames = nameDiscoverer.getParameterNames(method);

        Object[] values = new Object[parameterNames.length];
        for (int i = 0; i < parameterNames.length; i++) {
            values[i] = parseParameter(request, parameterTypes[i], parameterNames[i]);
        }

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), values);
        assertThat(mav.getObject("id")).isEqualTo(id);
        assertThat(mav.getObject("age")).isEqualTo(age);
    }

    @Test
    void java_bean() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("userId", "id");
        request.addParameter("password", "pw");
        request.addParameter("age", String.valueOf(25));

        Method method = getMethod("create_javabean", clazz.getDeclaredMethods());

        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] parameterNames = nameDiscoverer.getParameterNames(method);

        Object[] values = new Object[parameterTypes.length];
        for (int i = 0; i < parameterNames.length; i++) {
            values[i] = parseParameter(request, parameterTypes[i], parameterNames[i]);
        }

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), values);
        logger.debug("testUser: {}", mav.getObject("testUser"));
        TestUser testUser = (TestUser) mav.getObject("testUser");
        assertThat(testUser.getUserId()).isEqualTo("id");
        assertThat(testUser.getPassword()).isEqualTo("pw");
        assertThat(testUser.getAge()).isEqualTo(25);

    }

    @Test
    void show_pathvariable() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        long id = 3;
        request.addParameter("id", String.valueOf(id));
        request.setRequestURI("/users/" + id);

        Method method = getMethod("show_pathvariable", clazz.getDeclaredMethods());
        String path = method.getAnnotation(RequestMapping.class).value();

        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] parameterNames = nameDiscoverer.getParameterNames(method);

        Object[] values = new Object[parameterTypes.length];
        for (int i = 0; i < parameterNames.length; i++) {
            values[i] = parseParameter(request, parameterTypes[i], parameterNames[i]);
        }

        PathPatternParser pathPatternParser = new PathPatternParser();
        pathPatternParser.setMatchOptionalTrailingSeparator(true);

        PathPattern pathPattern = pathPatternParser.parse(path);

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), values);

        assertThat(method.getParameters()[0].getAnnotation(PathVariable.class)).isNotNull();
        assertThat(mav.getObject("id")).isEqualTo(id);
        assertThat(pathPattern.matches(PathContainer.parsePath(request.getRequestURI()))).isTrue();
    }

    private Object parseParameter(HttpServletRequest request, Class parameterType, String parameterName) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (parameterType.equals(String.class)) {
            logger.debug("parameter : {}", parameterName);
            return request.getParameter(parameterName);
        } else if (parameterType.isPrimitive()) {
            return parsePrimitive(request, parameterType, parameterName);
        } else {
            return parseNonPrimitive(request, parameterType);
        }
    }

    private Object parsePrimitive(HttpServletRequest request, Class parameterType, String parameterName) {
        logger.debug("parameterName: {}", parameterName);
        if (parameterType.equals(long.class)) {
            return Long.parseLong(request.getParameter(parameterName));
        }
        if (parameterType.equals(int.class)) {
            return Integer.parseInt(request.getParameter(parameterName));
        }
        throw new IllegalArgumentException();
    }

    private Object parseNonPrimitive(HttpServletRequest request, Class parameterType) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        logger.debug("parameterType: {}", parameterType);

        Constructor constructor = parameterType.getDeclaredConstructor();
        constructor.setAccessible(true);

        Object instance = constructor.newInstance();

        for (Field field : parameterType.getDeclaredFields()) {
            field.setAccessible(true);

            Class consParameterType = field.getType();
            String consParameterName = field.getName();

            if (consParameterType.equals(String.class)) {
                field.set(instance, request.getParameter(consParameterName));
            } else if (consParameterType.isPrimitive()) {
                field.set(instance, parsePrimitive(request, consParameterType, consParameterName));
            } else {
                field.set(instance, parseNonPrimitive(request, consParameterType));
            }
        }
        return instance;
    }

    private Method getMethod(String name, Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .get();
    }
}