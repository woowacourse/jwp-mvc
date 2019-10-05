package nextstep.mvc.tobe.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;

import static nextstep.mvc.tobe.view.JspView.DEFAULT_REDIRECT_PREFIX;
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

    @Test
    void render_redirect() throws Exception {
        String redirectUrl = "/";
        view = new JspView(String.format("%s%s", DEFAULT_REDIRECT_PREFIX, redirectUrl));
        view.render(new HashMap<>(), request, response);
        assertEquals(response.getStatus(), 302);
        assertEquals(response.getRedirectedUrl(), redirectUrl);
    }
}