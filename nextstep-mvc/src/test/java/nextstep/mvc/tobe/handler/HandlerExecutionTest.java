package nextstep.mvc.tobe.handler;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.MyController;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerExecutionTest {
    private static final Logger logger = LoggerFactory.getLogger(HandlerExecutionTest.class);

    @Test
    public void handle() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Class<MyController> clazz = MyController.class;
        Constructor<MyController> constructor = clazz.getDeclaredConstructor();
        Method method = clazz.getMethod("findUserId", HttpServletRequest.class, HttpServletResponse.class);
        HandlerExecution handlerExecution = new HandlerExecution(constructor.newInstance(), method);
        ModelAndView modelAndView = handlerExecution.handle(request, response);
        modelAndView.render(request, response);

        assertThat(response.getForwardedUrl()).isEqualTo("/users/view.jsp");
        response.getOutputStream().println();
    }
}