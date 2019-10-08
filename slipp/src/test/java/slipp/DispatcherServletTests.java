package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.ControllerHandlerAdaptor;
import nextstep.mvc.tobe.HandlerAdaptor;
import nextstep.mvc.exception.HandlerAdapterNotFoundException;
import nextstep.mvc.tobe.HandlerExecutionAdaptor;
import nextstep.mvc.exception.HandlerNotExistException;
import nextstep.web.annotation.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DispatcherServletTests {
    private DispatcherServlet dispatcherServlet;
    private MockHttpServletRequest mockHttpServletRequest;
    private MockHttpServletResponse mockHttpServletResponse;

    @BeforeEach
    void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletResponse = new MockHttpServletResponse();
    }

    @Test
    void service() throws ServletException, IOException {
        dispatcherServlet = new DispatcherServlet(Arrays.asList(new ControllerHandlerAdaptor(), new HandlerExecutionAdaptor()),
            Arrays.asList(new ManualHandlerMapping(), new AnnotationHandlerMapping("slipp")));
        dispatcherServlet.init();

        makeRequest();
        dispatcherServlet.service(mockHttpServletRequest, mockHttpServletResponse);
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(200);
    }

    @Test
    void HandlerAdapterNotFound() {
        List<HandlerAdaptor> list = new ArrayList<>();
        dispatcherServlet = new DispatcherServlet(list,
            Arrays.asList(new ManualHandlerMapping(), new AnnotationHandlerMapping("slipp")));
        dispatcherServlet.init();
        makeRequest();

        assertThrows(HandlerAdapterNotFoundException.class, () -> {
            dispatcherServlet.service(mockHttpServletRequest, mockHttpServletResponse);
        });
    }

    @Test
    void HandlerNotFound() {
        List<HandlerMapping> list = new ArrayList<>();
        dispatcherServlet = new DispatcherServlet(Arrays.asList(new ControllerHandlerAdaptor(), new HandlerExecutionAdaptor()), list);
        dispatcherServlet.init();
        makeRequest();

        assertThrows(HandlerNotExistException.class, () -> {
            dispatcherServlet.service(mockHttpServletRequest, mockHttpServletResponse);
        });
    }

    private void makeRequest() {
        mockHttpServletRequest.setMethod(RequestMethod.GET.toString());
        mockHttpServletRequest.setRequestURI("/");
        mockHttpServletRequest.setProtocol("HTTP/1.1");
        mockHttpServletRequest.addHeader("Host", "localhost:8080");
        mockHttpServletRequest.addHeader("Connection", "keep-alive");
        mockHttpServletRequest.addHeader("Accept", "*/*");
    }
}
