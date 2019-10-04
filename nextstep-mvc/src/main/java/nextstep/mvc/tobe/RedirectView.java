package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static nextstep.mvc.DispatcherServlet.DEFAULT_REDIRECT_PREFIX;

public class RedirectView implements View {
    String name;

    public RedirectView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(name.substring(DEFAULT_REDIRECT_PREFIX.length()));
    }
}
