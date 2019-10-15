package nextstep.mvc.tobe;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArgumentResolverTest {
    private static final Logger logger = LoggerFactory.getLogger(ArgumentResolverTest.class);

    private ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @Test
    void argumentResolver() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        String userId = "javajigi";
        request.addParameter("userId", userId);

        Class clazz = TestUserController.class;
        Method method = getMethod("requestParam", clazz.getDeclaredMethods());

        ArgumentResolver args = new ArgumentResolver(request, new MockHttpServletResponse());
        Object[] objects = args.resolve(method);

        boolean result = false;
        for (Object object : objects) {
            if (object.toString().equals(userId)) {
                result = true;
                break;
            }
        }
        assertTrue(result);
    }

    private Method getMethod(String name, Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .get();
    }
}
