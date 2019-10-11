package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RedirectView implements View {
    private final String redirectUrl;
    private final int statusCode;

    public RedirectView(String redirectUrl) {
        this(redirectUrl, 302);
    }

    public RedirectView(String redirectUrl, int statusCode) {
        this.redirectUrl = redirectUrl;
        this.statusCode = statusCode;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setStatus(statusCode);
        response.setHeader("Location", redirectUrl);
    }
}
