package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static nextstep.mvc.DispatcherServlet.DEFAULT_REDIRECT_PREFIX;

public class JspView implements View {
    String name;

    public JspView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        model.entrySet().stream()
                .forEach(entry->req.setAttribute(entry.getKey(),entry.getValue()));
        if (name.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(name.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }
        req.getRequestDispatcher(name).forward(req,resp);
    }
}
