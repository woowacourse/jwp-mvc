package nextstep.mvc.tobe;

import nextstep.mvc.mock.MyController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerExecutionTest {
    @Test
    @DisplayName("handle 메서드를 실행하면 ModelAndView를 결과로 되돌려준다.")
    void execution_notNullTest_ModelAndView() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/notnull");
        MockHttpServletResponse response = new MockHttpServletResponse();

        Method method = MyController.class.getDeclaredMethod("notNullTest", HttpServletRequest.class, HttpServletResponse.class);
        HandlerExecution execution = new HandlerExecution(method, method.getDeclaringClass().getConstructor().newInstance());

        assertThat(execution.handle(request, response)).isInstanceOf(ModelAndView.class);
    }
}