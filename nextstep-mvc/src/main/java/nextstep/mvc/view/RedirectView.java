package nextstep.mvc.view;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RedirectView implements View {
    public static final String REDIRECT_PREFIX = "redirect:";
    private final String path;

    public RedirectView(String path) {
        this.path = path;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        res.sendRedirect(StringUtils.stripStart(this.path, REDIRECT_PREFIX));
    }
}