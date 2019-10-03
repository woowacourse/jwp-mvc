package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class ListUserControllerTest extends AbstractControllerTest {
    @BeforeEach
    void setUp() {
        initialize();
    }

    @Test
    @DisplayName("레거시 컨트롤러를 바꾸었을때 정상 동작 - 리다이렉트인 경우")
    void listUserController() throws Exception {
        request = new MockHttpServletRequest("GET", "/users");
        response = new MockHttpServletResponse();

        ModelAndView modelAndView = mappings.handle(request, response);
        assertThat(modelAndView.getView().getViewName()).isEqualTo("redirect:/users/loginForm");
    }
}