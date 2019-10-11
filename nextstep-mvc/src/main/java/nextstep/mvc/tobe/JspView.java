package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View {
    private final String viewName;

    private final View forwardingView;

    private JspView(String viewName, View forwardingView) {
        this.viewName = viewName;
        this.forwardingView = forwardingView;
    }

    public static JspView from(String viewName) {
        return new JspView(viewName, RequestForwardingView.from(viewName));
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        forwardingView.render(model, request, response);
    }
}
