package nextstep.mvc.tobe.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View {
    private static final String JSP_SUFFIX = ".jsp";

    private String viewName;

    public JspView(String viewName) {
        setJspSuffix(viewName);
    }

    private void setJspSuffix(String viewName) {
        this.viewName = viewName + JSP_SUFFIX;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }
}
