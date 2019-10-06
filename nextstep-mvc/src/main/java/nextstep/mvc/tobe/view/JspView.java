package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.RequestContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View {
    private final String viewName;

    public JspView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, RequestContext requestContext) throws Exception {
        HttpServletRequest request = requestContext.getHttpServletRequest();
        HttpServletResponse response = requestContext.getHttpServletResponse();

        model.forEach(request::setAttribute);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
        requestDispatcher.forward(request, response);
    }
}
