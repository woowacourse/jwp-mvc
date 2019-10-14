package nextstep.mvc;

import nextstep.mvc.tobe.AbstractHandlerMapping;
import nextstep.mvc.tobe.handlerAdapter.RequestHandlerAdapter;
import nextstep.mvc.tobe.handlerAdapter.HandlerAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import slipp.ManualHandlerMapping;

import javax.servlet.ServletException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DispatcherServletTest {
    private DispatcherServlet dispatcherServlet;

    @BeforeEach
    void setup() {
        List<HandlerMapping> HandlerMappings = Arrays.asList(new ManualHandlerMapping(), new AbstractHandlerMapping("slipp"));
        List<HandlerAdapter> HandlerAdapters = Arrays.asList(new RequestHandlerAdapter(), new LegacyHandlerAdapter());
        dispatcherServlet = new DispatcherServlet(HandlerMappings, HandlerAdapters);
        dispatcherServlet.init();
    }

    @Test
    void getHandlerAdapter() throws ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/notexiestUrl");
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertThatThrownBy(() -> dispatcherServlet.service(request, response))
                .isInstanceOf(ServletException.class)
                .hasMessage("핸들러를 찾을 수 없습니다.");
    }
}