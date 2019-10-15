package nextstep.mvc.tobe.view;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;

import static nextstep.mvc.tobe.viewresolver.RedirectViewResolver.DEFAULT_REDIRECT_PREFIX;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RedirectViewTest {
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private View view;

    @Test
    void render_redirect() throws Exception {
        String redirectUrl = "/";
        view = new JspView(String.format("%s%s", DEFAULT_REDIRECT_PREFIX, redirectUrl));
        view.render(new HashMap<>(), request, response);
        assertEquals(response.getStatus(), 302);
        assertEquals(response.getRedirectedUrl(), redirectUrl);
    }
}
