package nextstep.mvc.tobe.view;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class JspViewTest {

    @Test
    public void render() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        View view = new JspView("/home.jsp");

        view.render(null, request, response);
        assertThat(response.getStatus()).isEqualTo(200);
    }
}