package nextstep.mvc.tobe.scanner;

import nextstep.mvc.tobe.handler.HandlerExecution;
import nextstep.mvc.tobe.handler.HandlerKey;
import nextstep.web.annotation.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMappingScannerTest {
    private Set<Class<?>> controllers;

    @BeforeEach
    void setUp() {
        Object[] basePackage = {"samples"};
        controllers = ControllerScanner.scan(basePackage);
    }

    @Test
    void requestMappingScan() {
        Map<HandlerKey, HandlerExecution> handlers = RequestMappingScanner.scan(controllers);
        assertThat(handlers.get(new HandlerKey("/users", RequestMethod.GET))).isNotNull();
        assertThat(handlers.get(new HandlerKey("/users", RequestMethod.POST))).isNotNull();
    }
}