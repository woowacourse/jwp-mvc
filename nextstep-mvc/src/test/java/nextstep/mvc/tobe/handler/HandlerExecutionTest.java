package nextstep.mvc.tobe.handler;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.MyController;
import nextstep.mvc.tobe.exception.ReturnTypeNotSupportedException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HandlerExecutionTest {

    @Test
    public void handleWhenReturnModelAndView() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Class<MyController> clazz = MyController.class;
        Constructor<MyController> constructor = clazz.getDeclaredConstructor();
        Method method = clazz.getMethod("findUserId", HttpServletRequest.class, HttpServletResponse.class);
        HandlerExecution handlerExecution = new HandlerExecution(constructor.newInstance(), method);
        ModelAndView modelAndView = handlerExecution.handle(request, response);
        modelAndView.render(request, response);

        assertThat(response.getForwardedUrl()).isEqualTo("/users/view.jsp");
    }

    @Test
    public void handleWhenReturnString() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Class<MyController> clazz = MyController.class;
        Constructor<MyController> constructor = clazz.getDeclaredConstructor();
        Method method = clazz.getMethod(
                "test_return_string",
                HttpServletRequest.class,
                HttpServletResponse.class);
        HandlerExecution handlerExecution = new HandlerExecution(constructor.newInstance(), method);
        ModelAndView modelAndView = handlerExecution.handle(request, response);
        modelAndView.render(request, response);

        assertThat(response.getForwardedUrl()).isEqualTo("/test.jsp");
    }

    @Test
    public void handleWhenNotSupportedReturnValue() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Class<MyController> clazz = MyController.class;
        Constructor<MyController> constructor = clazz.getDeclaredConstructor();
        Method method = clazz.getMethod(
                "test_not_support_string", HttpServletRequest.class, HttpServletResponse.class);
        HandlerExecution handlerExecution = new HandlerExecution(constructor.newInstance(), method);

        assertThrows(ReturnTypeNotSupportedException.class, () -> handlerExecution.handle(request, response));
    }
}