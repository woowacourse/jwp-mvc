package nextstep.mvc.tobe.core;

import nextstep.mvc.tobe.scanner.ControllerScanner;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerScannerTest {

    @Test
    void 컨트롤러애너테이션_스캐닝_확인() {
        Reflections reflections = new Reflections("nextstep.mvc.tobe");
        ControllerScanner scanner = new ControllerScanner(reflections);

        Map<HandlerKey, HandlerExecution> handlers = scanner.scan();

        assertThat(handlers.size()).isEqualTo(6);
    }
}