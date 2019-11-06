package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.RequestForwardingView;
import nextstep.mvc.tobe.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JspView jspView = (JspView) o;
        return Objects.equals(viewName, jspView.viewName) &&
                Objects.equals(forwardingView, jspView.forwardingView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(viewName, forwardingView);
    }
}
