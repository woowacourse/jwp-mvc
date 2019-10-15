package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class AdvancedMissionTest {
    private static final Logger logger = LoggerFactory.getLogger(AdvancedMissionTest.class);

    private AnnotationHandlerMapping handlerMapping;

    private static final Map<Class, Function<String, Object>> PRIMITIVE_TYPE_PARSER = new HashMap<>();

    static {
        PRIMITIVE_TYPE_PARSER.put(int.class, Integer::parseInt);
        PRIMITIVE_TYPE_PARSER.put(long.class, Long::parseLong);
        PRIMITIVE_TYPE_PARSER.put(double.class, Double::parseDouble);
        PRIMITIVE_TYPE_PARSER.put(byte.class, Byte::parseByte);
        PRIMITIVE_TYPE_PARSER.put(float.class, Float::parseFloat);
        PRIMITIVE_TYPE_PARSER.put(short.class, Short::parseShort);
    }

    @BeforeEach
    void setup() {
        handlerMapping = new AnnotationHandlerMapping("nextstep.mvc.tobe");
        handlerMapping.initialize();
    }

    @Test
    void primitiveType() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        String userId = "javajigi";
        String password = "password";
        request.addParameter("userId", userId);
        request.addParameter("password", password);

        ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        Class clazz = TestUserController.class;
        Method method = clazz.getMethod("create_string", String.class, String.class);

        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] params = new Object[parameterNames.length];
        for (int i = 0; i < parameterNames.length; i++) {
            params[i] = parsePrimitiveAndStringType(request.getParameter(parameterNames[i]), parameterTypes[i]);
        }

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), params);
        assertThat(mav.getObject("userId")).isEqualTo(userId);
        assertThat(mav.getObject("password")).isEqualTo(password);
    }

    @Test
    void notPrimitiveType() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users/object");
        String userId = "MasterOfJava";
        String password = "password";
        String age = "21";
        request.addParameter("userId", userId);
        request.addParameter("password", password);
        request.addParameter("age", age);

        ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        Class clazz = TestUserController.class;
        Method method = clazz.getMethod("create_javabean", TestUser.class);

        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] params = new Object[parameterNames.length];
        for (int i = 0; i < parameterNames.length; i++) {
            params[i] = processParameter(request, parameterNames[i], parameterTypes[i]);
        }

        ModelAndView mav = (ModelAndView) method.invoke(clazz.newInstance(), params);
        TestUser testUser = (TestUser) mav.getObject("testUser");
        assertThat(testUser.getUserId()).isEqualTo(userId);
        assertThat(testUser.getPassword()).isEqualTo(password);
        assertThat(testUser.getAge()).isEqualTo(Integer.parseInt(age));

    }

    private Object processParameter(HttpServletRequest request, String parameterName, Class parameterType) throws IllegalAccessException, InstantiationException {
        if (parameterType.isPrimitive() || parameterType == String.class) {
            return parsePrimitiveAndStringType(request.getParameter(parameterName), parameterType);
        }

        Object instance = parameterType.newInstance();
        for (Field field : parameterType.getDeclaredFields()) {
            field.setAccessible(true);
            field.set(instance, parsePrimitiveAndStringType(request.getParameter(field.getName()), field.getType()));
        }
        return instance;
    }

    private Object parsePrimitiveAndStringType(String value, Class parameterType) {
        return PRIMITIVE_TYPE_PARSER.getOrDefault(parameterType, key -> key).apply(value);
    }

    @Test
    void parseSomethingTest() {
        assertThat(parsePrimitiveAndStringType("1", int.class)).isEqualTo(1);
        assertThat(parsePrimitiveAndStringType("2", String.class)).isEqualTo("2");
        assertThat(parsePrimitiveAndStringType("3", long.class)).isEqualTo(3L);
    }

    @Test
    void pathPattern() {
        PathPattern pp = parse("/users/{id}");
        assertThat(pp.matches(PathContainer.parsePath("/users/1"))).isTrue();

        Map<String, String> uriVariables = pp.matchAndExtract(PathContainer.parsePath("/users/1")).getUriVariables();
        assertThat(uriVariables.get("id")).isEqualTo("1");
    }

    private PathPattern parse(String path) {
        PathPatternParser ppp = new PathPatternParser();
        ppp.setMatchOptionalTrailingSeparator(true);
        return ppp.parse(path);
    }
}
