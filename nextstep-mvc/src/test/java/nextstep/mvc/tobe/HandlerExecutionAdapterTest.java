package nextstep.mvc.tobe;

import nextstep.mvc.Handler;
import nextstep.mvc.HandlerAdapter;
import nextstep.mvc.tobe.handlermapping.HandlerExecution;
import nextstep.mvc.tobe.view.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HandlerExecutionAdapterTest {

    private HandlerAdapter handlerAdapter;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        handlerAdapter = new HandlerExecutionAdapter();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void root_경로로_forward하는_HandlerExecution_어뎁터_적용_테스트() throws Exception {
        HandlerExecution handler = (request, response) -> ModelAndView.forward("/");

        assertTrue(handlerAdapter.support(handler));
        assertThat(handlerAdapter.handle(handler, request, response)).isEqualTo(ModelAndView.forward("/"));
    }

    @Test
    void root_경로로_redirect하는_HandlerExecution_어뎁터_적용_테스트() throws Exception {
        HandlerExecution handler = (request, response) -> ModelAndView.redirect("/");

        assertTrue(handlerAdapter.support(handler));
        assertThat(handlerAdapter.handle(handler, request, response)).isEqualTo(ModelAndView.redirect("/"));
    }
}