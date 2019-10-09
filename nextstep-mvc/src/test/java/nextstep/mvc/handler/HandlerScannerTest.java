package nextstep.mvc.handler;

import nextstep.web.annotation.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerScannerTest {
    @Test
    @DisplayName("HandlerScanner가 @RequestMapping이 붙은 메서드를 잘 스캔하는지 확인한다")
    public void handlerScannerTest() {
        Reflections reflections = new Reflections("nextstep.mvc.helper");
        HandlerScanner handlerScanner = new HandlerScanner(reflections);
        Map<HandlerKey, HandlerExecution> handlerExecutions = handlerScanner.getHandlerExecutions();
        HandlerKey handlerKey = new HandlerKey("/users", RequestMethod.GET);

        assertThat(handlerExecutions.get(handlerKey)).isNotNull();
    }
}