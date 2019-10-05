package nextstep.mvc.tobe.scanner;

import nextstep.mvc.tobe.core.HandlerExecution;
import nextstep.mvc.tobe.core.HandlerKey;
import nextstep.web.annotation.RequestMethod;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerScannerTest {
    private ControllerScanner scanner;

    @BeforeEach
    void setUp() {
        scanner = new ControllerScanner(new Reflections("nextstep.mvc.tobe"));
    }

    @Test
    void 컨트롤러애너테이션_스캐닝_확인() {
        Map<HandlerKey, HandlerExecution> handlers = scanner.scan();

        assertThat(handlers.size()).isEqualTo(6);
        AssertionsForClassTypes.assertThat(handlers.get(HandlerKey.of("/users", RequestMethod.GET))).isNotNull();
        AssertionsForClassTypes.assertThat(handlers.get(HandlerKey.of("/users", RequestMethod.POST))).isNotNull();
        AssertionsForClassTypes.assertThat(handlers.get(HandlerKey.of("/sample", RequestMethod.GET))).isNotNull();
        AssertionsForClassTypes.assertThat(handlers.get(HandlerKey.of("/sample", RequestMethod.POST))).isNotNull();
        AssertionsForClassTypes.assertThat(handlers.get(HandlerKey.of("/sample", RequestMethod.DELETE))).isNotNull();
        AssertionsForClassTypes.assertThat(handlers.get(HandlerKey.of("/sample", RequestMethod.PUT))).isNotNull();
    }
}