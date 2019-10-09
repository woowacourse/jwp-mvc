package nextstep.mvc;

import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.ControllerAdaptor;
import nextstep.mvc.tobe.HandlerExecutionAdapter;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class DispatcherServletTest {
    private DispatcherServlet dispatcherServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        dispatcherServlet = new DispatcherServlet(
                Collections.singletonList(new AnnotationHandlerMapping("nextstep")),
                Arrays.asList(new ControllerAdaptor(), new HandlerExecutionAdapter()));
        dispatcherServlet.init();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void MyController_get() throws ServletException {
        request.setMethod(RequestMethod.GET.toString());
        request.setRequestURI("/users");
        request.setParameter("userId", "admin");
        request.setProtocol("HTTP/1.1");
        request.addHeader("Host", "localhost:8080");
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Accept", "*/*");

        dispatcherServlet.service(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 슬립_모듈_컨트롤러_스캔_테스트() {
        Reflections reflections = new Reflections("slipp");
        assertThat(reflections.getTypesAnnotatedWith(Controller.class).size()).isGreaterThan(0);
        // TODO: 톰캣에서는 되는데 단위테스트할 때는 안 됨. ㅠㅠ
    }
}