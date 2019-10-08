package nextstep.mvc.tobe;

import nextstep.mvc.Handler;
import nextstep.mvc.HandlerAdapter;
import nextstep.mvc.asis.ControllerHandlerAdapter;
import nextstep.mvc.tobe.exception.InvalidHandlerAdaptException;
import nextstep.mvc.tobe.view.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class HandlerAdapterManagerTest {

    private HandlerAdapterManager handlerAdapterManager;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        List<HandlerAdapter> handlerAdapters = Arrays.asList(new ControllerHandlerAdapter(), new HandlerExecutionAdapter());
        handlerAdapterManager = new HandlerAdapterManager(handlerAdapters);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void 지원하지_않는_Handler에_대한_예외처리() {
        Handler handler = (request, response) -> ModelAndView.forward("/");

        assertThrows(InvalidHandlerAdaptException.class, () -> handlerAdapterManager.handle(handler, request, response));
    }
}