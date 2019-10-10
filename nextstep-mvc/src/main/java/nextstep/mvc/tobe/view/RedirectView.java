package nextstep.mvc.tobe.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RedirectView implements View {
    private final String location;

    public RedirectView(String location) {
        this.location = location;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendRedirect(location);
    }
}
