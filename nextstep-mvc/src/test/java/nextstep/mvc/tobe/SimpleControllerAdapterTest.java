package nextstep.mvc.tobe;

import nextstep.mvc.asis.Controller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleControllerAdapterTest {
    private final HandlerAdapter handlerAdapter = new SimpleControllerAdapter();

    @Test
    @DisplayName("supports() Controller 지원 확인")
    void supportsTest() {
        // given
        final Controller controller = (request, response) -> null;

        // when & then
        assertThat(handlerAdapter.supports(controller)).isTrue();
    }
}