package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.TestUser;
import nextstep.mvc.tobe.WebRequest;
import nextstep.mvc.tobe.WebRequestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;

class ObjectMethodArgumentResolverTest {
    private ObjectMethodArgumentResolver resolver = new ObjectMethodArgumentResolver();
    private Class clazz = this.getClass();
    private Map<Class<?>, Parameter> params;
    private MockHttpServletRequest request;
    private WebRequest webRequest;
    private Method method;

    private String id = "1";
    private String age = "20";
    private String userId = "javajigi";
    private String password = "password";
    private String bool = "true";

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        method = clazz.getDeclaredMethod("sampleJavaBean", OnlyDefaultConstructorJavaBean.class);

        params = Stream.of(clazz.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("sample"))
                .map(Executable::getParameters)
                .flatMap(Arrays::stream)
                .collect(Collectors.toMap(Parameter::getType, x -> x));

        request = new MockHttpServletRequest();
        request.addParameter("userId", userId);
        request.addParameter("password", password);
        request.addParameter("id", id);
        request.addParameter("age", age);
        request.addParameter("bool", bool);

        webRequest = new WebRequestContext(request, null);
    }

    @Test
    void 기본생성자만_있는_javaBean_매핑() {
        // given
        final MethodParameter methodParameter = new MethodParameter(params.get(OnlyDefaultConstructorJavaBean.class), "javaBean", 1, method);

        // when
        final OnlyDefaultConstructorJavaBean actual = (OnlyDefaultConstructorJavaBean) resolver.resolveArgument(webRequest, methodParameter);

        // then
        assertThat(actual.getUserId()).isEqualTo(userId);
        assertThat(actual.getPassword()).isEqualTo(password);
        assertThat(actual.getAge()).isEqualTo(Long.parseLong(age));
    }

    void sampleJavaBean(final OnlyDefaultConstructorJavaBean javaBean) {
    }

}

class OnlyDefaultConstructorJavaBean {
    private String userId;
    private String password;
    private int age;

    public OnlyDefaultConstructorJavaBean() {
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }
}