package nextstep.mvc.tobe.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RedirectView implements View {
    public static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private String viewName;

    public RedirectView(String viewName) {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            this.viewName = viewName.substring(DEFAULT_REDIRECT_PREFIX.length());
            return;
        }
        this.viewName = viewName;
    }

    @Override
    public boolean isMapping(Object view) {
        if (view instanceof String) {
            return ((String) view).startsWith(DEFAULT_REDIRECT_PREFIX);
        }
        return false;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendRedirect(viewName);
    }
}
