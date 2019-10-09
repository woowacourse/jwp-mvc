package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.EmptyView;
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
    void execution_() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/notnull");
        MockHttpServletResponse response = new MockHttpServletResponse();
        Object controller = new MyController();
        Method method = MyController.class.getDeclaredMethod("returnNotNull", HttpServletRequest.class, HttpServletResponse.class);

        HandlerExecution execution = new HandlerExecution(controller, method);

        assertThat(execution.execute(request, response)).isInstanceOf(ModelAndView.class);
    }

    @Test
    @DisplayName("handle할 메서드가 Null을 반환하는 경우 EmptyView를 담아 ModelAndView를 되돌려준다.")
    void execution_findUserId_isNull() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        MockHttpServletResponse response = new MockHttpServletResponse();
        Object controller = new MyController();
        Method method = MyController.class.getDeclaredMethod("findUserId", HttpServletRequest.class, HttpServletResponse.class);

        HandlerExecution execution = new HandlerExecution(controller, method);

        assertThat(execution.execute(request, response).getView()).isInstanceOf(EmptyView.class);
    }
}