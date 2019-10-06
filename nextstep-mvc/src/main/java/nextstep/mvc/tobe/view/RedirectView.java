package nextstep.mvc.tobe.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class RedirectView implements View {
    String name;

    public RedirectView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(name);
    }
}
