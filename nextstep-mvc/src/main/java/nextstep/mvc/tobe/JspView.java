package nextstep.mvc.tobe;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View {
    private final String viewName;

    private JspView(String viewName) {
        this.viewName = viewName;
    }

    public static JspView from(String viewName) {
        return new JspView(viewName);
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDispatcher rd = request.getRequestDispatcher(viewName);

        // fill request with model
        for(String key : model.keySet()) {
            request.setAttribute(key, model.get(key));
        }

        rd.forward(request, response);
    }
}
