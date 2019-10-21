package nextstep.mvc;

import nextstep.mvc.tobe.support.ApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DispatcherServletTest {
    private DispatcherServlet dispatcherServlet;

    @BeforeEach
    void setup() {
        ApplicationContext applicationContext = new ApplicationContext("nextstep.mvc.tobe", "slipp");
        dispatcherServlet = new DispatcherServlet(applicationContext);
        dispatcherServlet.init();
    }

    @Test
    void getHandlerAdapter() throws ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/notExistUrl");
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertThatThrownBy(() -> dispatcherServlet.service(request, response))
                .isInstanceOf(ServletException.class)
                .hasMessage("핸들러를 찾을 수 없습니다.");
    }
}