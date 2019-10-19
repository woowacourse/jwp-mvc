package nextstep.mvc.tobe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArgumentResolverTest {
    private static final Logger logger = LoggerFactory.getLogger(ArgumentResolverTest.class);

    private static final String DEFAULT_USER_ID = "javajigi";

    private Class clazz;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        this.clazz = TestUserController.class;
        this.request = new MockHttpServletRequest();
        this.response = new MockHttpServletResponse();

        this.request.addParameter("userId", DEFAULT_USER_ID);
    }

    @Test
    @DisplayName("정상적으로 ArgumentResolver를 생성한다.")
    void createArgumentResolver() {
        assertThat(new ArgumentResolver(request, response)).isNotNull();
    }

    @Test
    @DisplayName("메소드의 파라미터를 가져온다. (TestUserController -> requestParam)")
    void requestParam() {
        Method method = getMethod("requestParam", clazz.getDeclaredMethods());
        ArgumentResolver args = new ArgumentResolver(request, response);

        List<Object> parameters = Arrays.asList(args.resolve(method));
        parameters = parameters.stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        assertTrue(parameters.contains(DEFAULT_USER_ID));
    }

    @Test
    @DisplayName("메소드 파라미터 타입이 맞지 않아 예외가 발생한다. (TestUserController -> create_string)")
    void create_string() {
        Method method = getMethod("create_string", clazz.getDeclaredMethods());
        ArgumentResolver args = new ArgumentResolver(request, response);

        assertThrows(NotMatchParameterException.class, () -> args.resolve(method));
    }

    @Test
    @DisplayName("메소드 파라미터 타입이 맞지 않아 예외가 발생한다. (TestUserController -> show_pathvariable)")
    void show_pathvariable() {
        Method method = getMethod("show_pathvariable", clazz.getDeclaredMethods());
        ArgumentResolver args = new ArgumentResolver(request, response);

        assertThrows(NotMatchParameterException.class, () -> args.resolve(method));
    }

    private Method getMethod(String name, Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .get();
    }
}
