package nextstep.mvc.tobe;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View {

    private String viewName;

    public JspView(String viewName) {
        this.viewName = viewName;
    }

    public JspView(Object viewName) {
        checkType(viewName);
        this.viewName = (String) viewName;
    }

    private void checkType(Object viewName) {
        if (!(viewName instanceof String)) {
            throw new IllegalArgumentException("올바른 타입이 아닙니다.");
        }
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDispatcher rd = request.getRequestDispatcher(viewName);
        rd.forward(request, response);
    }
}
