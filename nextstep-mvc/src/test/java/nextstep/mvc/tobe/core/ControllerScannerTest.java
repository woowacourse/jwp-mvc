package nextstep.mvc.tobe.core;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerScannerTest {
    private ComponentScanner scanner;

    @Test
    void 컨트롤러애너테이션_스캐닝_확인() {
        scanner = new ControllerScanner("nextstep.mvc.tobe");
        Object result = scanner.scan();

        assertThat(result).isNotNull();
        assertThat(result instanceof HashMap).isTrue();

        Map<HandlerKey, HandlerExecution> handlers = (Map<HandlerKey, HandlerExecution>) result;

        assertThat(handlers.size()).isEqualTo(2);
    }
}