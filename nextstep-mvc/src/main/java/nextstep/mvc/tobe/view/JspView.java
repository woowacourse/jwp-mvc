package nextstep.mvc.tobe.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

public class JspView implements View {
    private final String view;

    public JspView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JspView jspView = (JspView) o;
        return Objects.equals(view, jspView.view);
    }

    @Override
    public int hashCode() {
        return Objects.hash(view);
    }

    @Override
    public String toString() {
        return "JspView{" +
                "view='" + view + '\'' +
                '}';
    }
}
