package nextstep.mvc.tobe.view;

import nextstep.mvc.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RedirectView implements View {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final String redirectUrl;

    public RedirectView(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendRedirect(redirectUrl.substring(DEFAULT_REDIRECT_PREFIX.length()));
    }
}
