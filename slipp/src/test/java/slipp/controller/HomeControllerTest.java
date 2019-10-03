package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class HomeControllerTest extends AbstractControllerTest {

    @BeforeEach
    void setUp() {
        initialize();
    }

    @Test
    @DisplayName("레거시 컨트롤러를 바꾸었을때 정상 동작")
    void homeControllerTest() throws Exception {
        request = new MockHttpServletRequest("GET", "/");
        response = new MockHttpServletResponse();

        ModelAndView modelAndView = mappings.handle(request, response);

        assertThat(modelAndView.getView().getViewName()).isEqualTo("home.jsp");
        assertThat(request.getAttribute("users")).isNotNull();
    }
}