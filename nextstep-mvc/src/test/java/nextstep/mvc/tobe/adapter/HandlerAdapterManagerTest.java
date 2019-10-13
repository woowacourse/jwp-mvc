package nextstep.mvc.tobe.adapter;

import nextstep.mvc.tobe.handler.HandlerExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerAdapterManagerTest {
    private HandlerAdapterManager handlerAdapterManager;

    @BeforeEach
    void setUp() {
        handlerAdapterManager = new HandlerAdapterManager();
    }

    @Test
    void findHandlerExecutionHandlerAdapter() throws NotFoundAdapterException {
        assertThat(handlerAdapterManager.findHandlerAdapter(new HandlerExecution(null, null)).getClass())
                .isEqualTo(HandlerExecutionHandlerAdapter.class);
    }
}