package nextstep.mvc.tobe;


import nextstep.mvc.asis.Controller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultHandlerAdapterTest {
    private final HandlerAdapter handlerAdapter = new DefaultHandlerAdapter();
    private final Handler handler = mock(HandlerExecution.class);

    @Test
    @DisplayName("supports() HandlerExecution, Controller 지원 확인")
    void supportsTest() {
        // given
        final Controller controller = (request, response) -> null;
        final HandlerExecution handlerExecution = (request, response) -> null;

        // when & then
        assertThat(handlerAdapter.supports(controller)).isTrue();
        assertThat(handlerAdapter.supports(handlerExecution)).isTrue();
    }

    @Test
    @DisplayName("handle() String to ModelAndView ")
    void StringToModelAndView() throws Exception {
        // given
        when(handler.execute(any(), any())).thenReturn("view");

        // when & then
        assertDoesNotThrow(() -> handlerAdapter.handle(any(), any(), handler));
    }

    @Test
    @DisplayName("handle() 지원하지 않는 값을 리턴 받을 경우 예외처리")
    void handleTest() throws Exception {
        // given
        when(handler.execute(any(), any())).thenReturn(123);

        // when & then
        assertThrows(ClassCastException.class, () -> handlerAdapter.handle(any(), any(), handler));

    }
}
