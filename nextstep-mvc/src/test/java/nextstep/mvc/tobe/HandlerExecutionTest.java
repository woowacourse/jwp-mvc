package nextstep.mvc.tobe;

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
    @DisplayName("handle할 메서드가 Null을 반환하는 경우 ")
    void execution_findUserId_isNull() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        MockHttpServletResponse response = new MockHttpServletResponse();

        Method method = MyController.class.getDeclaredMethod("findUserId", HttpServletRequest.class, HttpServletResponse.class);
        HandlerExecution execution = new HandlerExecution(method, method.getDeclaringClass().getConstructor().newInstance());
        assertThat(execution.handle(request, response).getView()).isInstanceOf(EmptyView.class);
    }

    @Test
    @DisplayName("handle 메서드를 실행하면 ModelAndView를 결과로 되돌려준다.")
    void execution_notNullTest_ModelAndView() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/notnull");
        MockHttpServletResponse response = new MockHttpServletResponse();

        Method method = MyController.class.getDeclaredMethod("notNullTest", HttpServletRequest.class, HttpServletResponse.class);
        HandlerExecution execution = new HandlerExecution(method,method.getDeclaringClass().getConstructor().newInstance());

        assertThat(execution.handle(request, response)).isInstanceOf(ModelAndView.class);
    }
}