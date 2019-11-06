package slipp.controller;

import nextstep.mvc.handler.handlermapping.HandlerExecution;
import nextstep.mvc.handler.handlermapping.HandlerExecutionFactory;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerExecutionFactoryTest {

    @Test
    void test() throws NoSuchMethodException {
        HandlerExecutionFactory factory = HandlerExecutionFactory.getInstance();

        HandlerExecution execution = factory.fromMethod(ForwardController.class.getMethod("handleUserForm", HttpServletRequest.class, HttpServletResponse.class));

        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        assertThat(execution.handle(request, response)).isEqualTo(new ModelAndView(JspView.from("/user/form.jsp")));
    }
}