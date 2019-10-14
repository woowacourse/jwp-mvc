package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RedirectView implements View {
    private final String redirectPath;

    private RedirectView(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public static RedirectView from(String redirectPath) {
        return new RedirectView(redirectPath);
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendRedirect(redirectPath);
    }
}
