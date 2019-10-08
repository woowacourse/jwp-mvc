package nextstep.mvc.asis;

import nextstep.mvc.HandlerAdapter;
import nextstep.mvc.tobe.view.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ControllerHandlerAdapterTest {

    private HandlerAdapter handlerAdapter;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        handlerAdapter = new ControllerHandlerAdapter();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void redirect_ForwardController_어뎁터_적용_테스트() throws Exception {
        Controller forwardController = new ForwardController("redirect:/");

        assertTrue(handlerAdapter.support(forwardController));

        ModelAndView result = handlerAdapter.handle(forwardController, request, response);
        assertThat(result).isEqualTo(ModelAndView.redirect("/"));
    }

    @Test
    void forwardController_어뎁터_적용_테스트() throws Exception {
        Controller forwardController = new ForwardController("/");
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        assertTrue(handlerAdapter.support(forwardController));

        ModelAndView result = handlerAdapter.handle(forwardController, request, response);
        assertThat(result).isEqualTo(ModelAndView.forward("/"));
    }


}