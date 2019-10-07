package nextstep.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RedirectView implements View {

    private final String redirectViewName;

    public RedirectView(String redirectViewName) {
        this.redirectViewName = redirectViewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendRedirect(redirectViewName);
    }
}
