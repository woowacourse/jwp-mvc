package nextstep.mvc.adapter;

import nextstep.mvc.HandlerExecution;
import nextstep.mvc.asis.Controller;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerExecutionAdapterTest {
    private ExecutionAdapter adapter = new HandlerExecutionAdapter();

    private class TestController implements Controller {
        @Override
        public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
            return null;
        }
    }

    @Test
    void matchHandlerExecution() {
        HandlerExecution handlerExecution = new HandlerExecution(null, null);

        assertThat(adapter.matchClass(handlerExecution)).isTrue();
    }

    @Test
    void mismatchHandlerExecution() {
        Controller controller = new TestController();

        assertThat(adapter.matchClass(controller)).isFalse();
    }
}
