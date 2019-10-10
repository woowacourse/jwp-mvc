package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.Car;
import nextstep.mvc.tobe.WebRequest;
import nextstep.mvc.tobe.WebRequestContext;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.PathVariable;
import nextstep.web.annotation.RequestBody;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import nextstep.web.support.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.ServletInputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyMethodArgumentResolverTest {
    private RequestBodyMethodArgumentResolver resolver = new RequestBodyMethodArgumentResolver();
    private WebRequest webRequest;
    private MockHttpServletRequest request;
    private Method method;
    private List<MethodParameter> methodParams;

    @BeforeEach
    void setUp() {
        method = Stream.of(this.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals("foo"))
                .findAny()
                .get();

        methodParams = new MethodParameters(method).getMethodParams();

        request = new MockHttpServletRequest();
        webRequest = new WebRequestContext(request, null);
    }

    @Test
    void supports_RequestBody_인경우_true() {
        assertThat(resolver.supports(methodParams.get(0))).isTrue();
    }

    @Test
    void supports_RequestBody_아닌경우_false() {
        assertThat(resolver.supports(methodParams.get(1))).isFalse();
    }

    @Test
    void resolveArgument_to_json_매핑_확인() {
        // given
        final String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        final Car expected = JsonUtils.toObject(json, Car.class);
        request.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        request.setContent(json.getBytes());

        // when
        final Object actual = resolver.resolveArgument(webRequest, methodParams.get(0));

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @RequestMapping(value = "/users/{id}/{userId}", method = RequestMethod.GET)
    public Car foo(@RequestBody Car car, @PathVariable long userId) {
        return car;
    }
}