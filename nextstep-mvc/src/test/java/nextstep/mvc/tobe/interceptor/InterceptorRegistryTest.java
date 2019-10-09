package nextstep.mvc.tobe.interceptor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InterceptorRegistryTest {
    private InterceptorRegistry registry = new InterceptorRegistry();
    private HandlerInterceptor interceptor1;
    private HandlerInterceptor interceptor2;

    @BeforeEach
    void setUp() {
        interceptor1 = new HandlerInterceptor() {
        };
        interceptor2 = new HandlerInterceptor() {
        };

        registry.addInterceptor(interceptor1)
                .addPathPatterns("/users")
                .addPathPatterns("/users/**")
                .excludePathPatterns("/users/login");

        registry.addInterceptor(interceptor2)
                .addPathPatterns("/users")
                .addPathPatterns("/users/**");
    }

    @Test
    @DisplayName("path 일치하는 인터셉터 여러개 가져오는 테스트")
    void test01() {
        final List<HandlerInterceptor> actual = registry.getHandlerInterceptors("/users");

        assertThat(actual)
                .hasSize(2)
                .contains(interceptor1)
                .contains(interceptor2);
    }

    @Test
    @DisplayName("PathPatterns 은 모두 일치하지만 excludePathPatterns 도 일치하는 인터셉터가 있는 경우")
    void test02() {
        final List<HandlerInterceptor> actual = registry.getHandlerInterceptors("/users/login");

        assertThat(actual)
                .hasSize(1)
                .contains(interceptor2);
    }


}