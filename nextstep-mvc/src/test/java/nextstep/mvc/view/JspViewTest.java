package nextstep.mvc.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class JspViewTest {
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private View view;

    @BeforeEach
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void redirectTest() throws Exception {
        view = new JspView("redirect:/");
        view.render(new HashMap<>(), request, response);

        assertThat(response.getStatus()).isEqualTo(302);
        assertThat(response.getHeader("Location")).isEqualTo("/");
    }

    @Test
    public void forwardTest() throws Exception {
        view = new JspView("home.jsp");
        view.render(new HashMap<>(), request, response);

        assertThat(response.getStatus()).isEqualTo(200);
    }
}