package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.http.server.PathContainer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AdvancedMissionTest {
    private static final Logger logger = LoggerFactory.getLogger(AdvancedMissionTest.class);

    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    void setup() {
        handlerMapping = new AnnotationHandlerMapping("nextstep.mvc.tobe");
        handlerMapping.initialize();
    }

    @Test
    void defaultTest() {
        Set<Method> requestMappingAnnotationPresentMethod = new ComponentScan(new Reflections("nextstep.mvc.tobe")).getRequestMappingAnnotationPresentMethod();
        logger.debug("{}", requestMappingAnnotationPresentMethod);
    }

    @Test
    void test() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        request.addParameter("userId", "javajigi");
        request.addParameter("password", "password");

        MockHttpServletResponse response = new MockHttpServletResponse();

        HandlerExecution handler = handlerMapping.getHandler(request);
        ModelAndView handle = handler.handle(request, response);
        System.out.println(handle.getView());
    }

    @Test
    void primitiveType() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        request.addParameter("userId", "javajigi");
        request.addParameter("password", "password");

        ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        Class clazz = TestUserController.class;
        Method method = clazz.getMethod("create_string", String.class, String.class);

        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] params = new Object[parameterNames.length];
        for (int i = 0; i < parameterNames.length; i++) {
            params[i] = parsePrimitiveAndStringType(request.getParameter(parameterNames[i]), parameterTypes[i]);
        }

        method.invoke(clazz.newInstance(), params);
    }

    @Test
    void notPrimitiveType() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users/object");
        request.addParameter("userId", "MasterOfJava");
        request.addParameter("password", "password");
        request.addParameter("age", "21");

        ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        Class clazz = TestUserController.class;
        Method method = clazz.getMethod("create_javabean", TestUser.class);

        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] params = new Object[parameterNames.length];
        for (int i = 0; i < parameterNames.length; i++) {
            params[i] = processParameter(request, parameterNames[i], parameterTypes[i]);
        }

        method.invoke(clazz.newInstance(), params);
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


    @Test
    void parseSomethingTest() {
        assertThat(parsePrimitiveAndStringType("1", int.class)).isEqualTo(1);
        assertThat(parsePrimitiveAndStringType("2", String.class)).isEqualTo("2");
        assertThat(parsePrimitiveAndStringType("3", long.class)).isEqualTo(3L);
    }

    private Object parsePrimitiveAndStringType(String value, Class parameterType) {
        if (parameterType.equals(int.class)) {
            return Integer.parseInt(value);
        }
        if (parameterType.equals(long.class)) {
            return Long.parseLong(value);
        }
        if (parameterType.equals(double.class)) {
            return Double.parseDouble(value);
        }
        if (parameterType.equals(byte.class)) {
            return Byte.parseByte(value);
        }
        if (parameterType.equals(float.class)) {
            return Float.parseFloat(value);
        }
        return value;
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

    private PathContainer toPathContainer(String path) {
        if (path == null) {
            return null;
        }
        return PathContainer.parsePath(path);
    }
}
