package nextstep.mvc.tobe.scanner;

import nextstep.mvc.tobe.MyController;
import nextstep.mvc.tobe.core.HandlerExecution;
import nextstep.mvc.tobe.core.HandlerKey;
import nextstep.web.annotation.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestMappingScannerTest {
    private Map<Class<?>, Object> controllers;
    private RequestMappingScanner scanner;

    @BeforeEach
    void setUp() {
        scanner = new RequestMappingScanner();
    }

    @Test
    void 리퀘스트매핑_스캐닝_확인() {
        controllers = new HashMap<>() {{
            put(MyController.class, new MyController());
        }};

        Map<HandlerKey, HandlerExecution> handlers = scanner.scan(controllers);

        assertThat(handlers.size()).isEqualTo(6);
        assertThat(handlers.get(HandlerKey.of("/users", RequestMethod.GET))).isNotNull();
        assertThat(handlers.get(HandlerKey.of("/sample", RequestMethod.PUT))).isNotNull();
    }

    @Test
    void 리퀘스트매핑_애너테이션_없는_경우() {
        controllers = new HashMap<>() {{
            put(RequestMappingScannerTest.class, new RequestMappingScannerTest());
        }};

        Map<HandlerKey, HandlerExecution> handlers = scanner.scan(controllers);

        assertThat(handlers.size()).isEqualTo(0);
    }
}