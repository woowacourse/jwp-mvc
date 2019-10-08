package nextstep.mvc.tobe.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

public class JspView implements View {
    private final String viewName;

    public JspView(final String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        RequestDispatcher rd = request.getRequestDispatcher(viewName);
        rd.forward(request, response);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final JspView jspView = (JspView) o;
        return Objects.equals(viewName, jspView.viewName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(viewName);
    }
}
