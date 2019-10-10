package nextstep.mvc.adapter.result;

import nextstep.mvc.ModelAndView;
import nextstep.mvc.exception.ViewNameResolveException;
import nextstep.mvc.view.JspView;
import nextstep.mvc.view.RedirectView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringExecutionResultAdapterTest {
    private ExecutionResultAdapter adapter;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        adapter = new StringExecutionResultAdapter();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void match() {
        Object o = "";
        assertThat(adapter.matchClass(o)).isTrue();
    }

    @Test
    void misMatch() {
        Object o = 1;
        assertThat(adapter.matchClass(o)).isFalse();
    }

    @Test
    void redirectView() {
        ModelAndView modelAndView = adapter.handle(request, response, "redirect:test");

        assertThat(modelAndView.getView()).isInstanceOf(RedirectView.class);
    }

    @Test
    void jspView() {
        ModelAndView modelAndView = adapter.handle(request, response, "test.jsp");

        assertThat(modelAndView.getView()).isInstanceOf(JspView.class);
    }

    @Test
    void notFoundView() {
        assertThatThrownBy(() -> adapter.handle(request, response, "invalid"))
                .isInstanceOf(ViewNameResolveException.class);
    }
}