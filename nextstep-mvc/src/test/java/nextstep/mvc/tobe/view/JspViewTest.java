package nextstep.mvc.tobe.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JspViewTest {
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private View view;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void render_jsp_file() throws Exception {
        view = new JspView("/user/login.jsp");
        view.render(new HashMap<>(), request, response);
        assertEquals(response.getStatus(), 200);
    }
}