package nextstep.mvc.view;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class JspViewTest {
    @Test
    public void forwardTest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        View view = new JspView("home.jsp");
        view.render(new HashMap<>(), request, response);

        assertThat(response.getStatus()).isEqualTo(200);
    }
}