package nextstep.mvc.adapter;

import nextstep.mvc.HandlerExecution;
import nextstep.mvc.asis.Controller;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerAdapterTest {
    private ExecutionAdapter adapter = new ControllerAdapter();

    private class TestController implements Controller {
        @Override
        public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
            return null;
        }
    }

    @Test
    void matchController() {
        Controller controller = new TestController();
        System.out.println(controller.getClass());

        assertThat(adapter.matchClass(controller)).isTrue();
    }

    @Test
    void misMatchController() {
        HandlerExecution handlerExecution = new HandlerExecution(null, null);

        assertThat(adapter.matchClass(handlerExecution)).isFalse();
    }
}