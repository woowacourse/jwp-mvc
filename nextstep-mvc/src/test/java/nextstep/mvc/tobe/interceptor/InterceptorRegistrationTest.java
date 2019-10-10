package nextstep.mvc.tobe.interceptor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InterceptorRegistrationTest {
    private HandlerInterceptor interceptor;
    private List<String> includePatters;
    private List<String> excludePatterns;
    private InterceptorRegistration registration;

    @BeforeEach
    void setUp() {
        interceptor = new HandlerInterceptor() {
        };
        includePatters = Arrays.asList("/users/**", "/user/**/edit");
        excludePatterns = Arrays.asList("/users/login");

        registration = InterceptorRegistration.from(interceptor)
                .addPathPatterns(includePatters)
                .excludePathPatterns(excludePatterns);
    }

    @Test
    @DisplayName("includePattern만 일치하는 경우 true")
    void matchTest01() {
        final String url = "/users/id";

        assertThat(registration.match(url)).isTrue();
    }

    @Test
    @DisplayName("includePattern '**' 중앙에 있는 경우 true 확인")
    void matchTest02() {
        final String url = "/user/1/edit";

        assertThat(registration.match(url)).isTrue();
    }

    @Test
    @DisplayName("includePattern만 일치하지 않으면 false")
    void matchTest03() {
        final String url = "/user";

        assertThat(registration.match(url)).isFalse();
    }

    @Test
    @DisplayName("includePattern, excludePattern 둘 다 일치 false")
    void matchTest04() {
        final String url = "/users/login";

        assertThat(registration.match(url)).isFalse();
    }
}