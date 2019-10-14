package slipp.controller;

import nextstep.mvc.core.RequestHandlers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class HomeControllerTest {
    private RequestHandlers mappings;

    @BeforeEach
    void setUp() {
        mappings = new RequestHandlers("slipp.controller");
        mappings.initialize();
    }

    @Test
    @DisplayName("레거시 컨트롤러를 바꾸었을때 정상 동작")
    void homeControllerTest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/");
        MockHttpServletResponse response = new MockHttpServletResponse();

        mappings.handle(request, response);

        assertThat(request.getAttribute("users")).isNotNull();
    }
}