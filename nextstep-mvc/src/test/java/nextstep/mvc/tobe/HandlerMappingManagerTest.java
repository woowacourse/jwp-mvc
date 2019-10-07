package nextstep.mvc.tobe;

import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.handlermapping.AnnotationHandlerMapping;
import nextstep.mvc.tobe.handlermapping.HandlerExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import samples.TestHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HandlerMappingManagerTest {

    private HandlerMappingManager manager;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        List<HandlerMapping> handlerMappings = Arrays.asList(
                new TestHandlerMapping(),
                new AnnotationHandlerMapping("samples")
        );
        manager = new HandlerMappingManager(handlerMappings);
        manager.initialize();
    }

    @Test
    @DisplayName("/test1 requestURI에 알맞는 Handler를 가져올 수 있는지 테스트")
    void test1_매핑_테스트() throws Exception {
        request = new MockHttpServletRequest(HttpMethod.GET.name(), "/test1");
        response = new MockHttpServletResponse();
        Controller handler = (Controller) manager.getHandler(request);

        assertThat(handler.handle(request, response)).isEqualTo("test1");
    }

    @Test
    @DisplayName("/test2 requestURI에 알맞는 Handler를 가져올 수 있는지 테스트")
    void test2_매핑_테스트() throws Exception {
        request = new MockHttpServletRequest(HttpMethod.GET.name(), "/test2");
        response = new MockHttpServletResponse();
        Controller handler = (Controller) manager.getHandler(request);

        assertThat(handler.handle(request, response)).isEqualTo("test2");
    }

    @Test
    @DisplayName("AnnotationHandlerMapping을 사용하는 헨들러가 제대로 매핑이 되는지 테스트")
    void users_매핑_테스트() {
        request = new MockHttpServletRequest(HttpMethod.GET.name(), "/users");
        response = new MockHttpServletResponse();

        HandlerExecution handler = (HandlerExecution) manager.getHandler(request);

        assertNotNull(handler);
    }
}
