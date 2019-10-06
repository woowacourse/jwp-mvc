package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RedirectView implements View {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final String path;

    public RedirectView(String path) {
        this.path = path;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendRedirect(path.substring(DEFAULT_REDIRECT_PREFIX.length()));
    }
}
