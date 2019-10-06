package nextstep.mvc;

import nextstep.mvc.exception.ExecutionHandleException;
import nextstep.mvc.tobe.Execution;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExecutionHandlerTest {
    private MockHttpServletRequest request = new MockHttpServletRequest();
    private MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    @DisplayName("Execution이 null 인 경우")
    void nullExecution() throws Exception {
        ModelAndView modelAndView = ExecutionHandler.handle(null, request, response);

        assertThat(modelAndView.getView()).isInstanceOf(JspView.class);
    }

    @Test
    @DisplayName("Execution이 redirect:로 시작하는 String을 반환하는 경우")
    void redirectView() throws Exception {
        Execution execution = (request, response) -> "redirect:test.txt";
        ModelAndView modelAndView = ExecutionHandler.handle(execution, request, response);

        assertThat(modelAndView.getView()).isInstanceOf(RedirectView.class);
    }

    @Test
    @DisplayName("Execution이 .jsp로 끝나는 String을 반환하는 경우")
    void jspView() throws Exception {
        Execution execution = (request, response) -> "test.jsp";
        ModelAndView modelAndView = ExecutionHandler.handle(execution, request, response);

        assertThat(modelAndView.getView()).isInstanceOf(JspView.class);
    }

    @Test
    @DisplayName("Execution이 처리 할 수 없는 String을 반환하는 경우")
    void stringException() {
        Execution execution = (request, response) -> "exception";

        assertThatThrownBy(() -> ExecutionHandler.handle(execution, request, response))
                .isInstanceOf(ExecutionHandleException.class);
    }

    @Test
    @DisplayName("Execution이 ModelAndView을 반환하는 경우")
    void modelAndView() throws Exception {
        ModelAndView modelAndView = new ModelAndView((model, request, response) -> {
        });
        Execution execution = (request, response) -> modelAndView;
        ModelAndView result = ExecutionHandler.handle(execution, request, response);

        assertThat(modelAndView).isEqualTo(result);
    }

    @Test
    @DisplayName("Execution이 처리 할 수 없는 객체를 반환하는 경우")
    void exception() {
        Execution execution = (request, response) -> 1;

        assertThatThrownBy(() -> ExecutionHandler.handle(execution, request, response))
                .isInstanceOf(ExecutionHandleException.class);
    }
}