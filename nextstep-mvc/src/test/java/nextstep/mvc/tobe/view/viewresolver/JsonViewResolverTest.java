package nextstep.mvc.tobe.view.viewresolver;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.ViewType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonViewResolverTest {
    private JsonViewResolver jsonViewResolver;

    @BeforeEach
    void setUp() {
        jsonViewResolver = new JsonViewResolver();
    }

    @Test
    @DisplayName("support() Success")
    void supportSuccess() {
        assertThat(jsonViewResolver.support(ViewType.JSON_VIEW)).isTrue();
    }

    @Test
    @DisplayName("support() Fail")
    void supportFail() {
        assertThat(jsonViewResolver.support(ViewType.JSP_VIEW)).isFalse();
    }

    @Test
    @DisplayName("resolve Ok")
    void resolveTest() {
        assertThat(jsonViewResolver.resolve("")).isInstanceOf(JsonView.class);
    }
}