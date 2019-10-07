package nextstep.mvc.tobe.adapter;


import nextstep.mvc.tobe.handler.HandlerExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HandlerExecutionAdapterTest {
    private final HandlerAdapter handlerAdapter = new HandlerExecutionAdapter();
    private final HandlerExecution handler = mock(HandlerExecution.class);
    private MockHttpServletResponse response;
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        when(handler.getMethod()).thenReturn(this.getClass().getDeclaredMethod("StringToModelAndView"));
        response = new MockHttpServletResponse();
        request = new MockHttpServletRequest();
    }

    @Test
    @DisplayName("supports() HandlerExecution 지원 확인")
    void supportsTest() {
        // when & then
        assertThat(handlerAdapter.supports(handler)).isTrue();
    }

    @Test
    @DisplayName("handle() String to ModelAndView ")
    void StringToModelAndView() throws Exception {
        // given
        when(handler.execute(any())).thenReturn("view");

        // when & then
        assertDoesNotThrow(() -> handlerAdapter.handle(request, response, handler));
    }

    @Test
    @DisplayName("handle() 지원하지 않는 값을 리턴 받을 경우 예외처리")
    void handleTest() throws Exception {
        // given
        when(handler.execute(any())).thenReturn(123);

        // when & then
        assertThrows(ClassCastException.class, () -> handlerAdapter.handle(request, response, handler));
    }
}
