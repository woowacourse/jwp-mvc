package nextstep.mvc.tobe.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View {
    private final String path;

    public JspView(String path) {
        this.path = path;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        RequestDispatcher rd = req.getRequestDispatcher(path);
        setAttribute(model, req);
        rd.forward(req, resp);
    }

    private void setAttribute(Map<String,?> model, HttpServletRequest req) {
        model.keySet().stream().forEach(key -> req.setAttribute(key, model.get(key)));
    }
}
