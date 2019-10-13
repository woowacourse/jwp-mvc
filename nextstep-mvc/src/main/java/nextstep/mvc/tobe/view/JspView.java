package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JspView implements View {
    private static final Logger logger = LoggerFactory.getLogger(JspView.class);

    private final String viewName;

    public JspView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, RequestContext requestContext) {
        HttpServletRequest request = requestContext.getHttpServletRequest();
        HttpServletResponse response = requestContext.getHttpServletResponse();

        model.forEach(request::setAttribute);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException | IllegalStateException e) {
            logger.error("Http Request Exception : ", e);
            throw new RequestForwardingFailedException();
        }
    }
}
