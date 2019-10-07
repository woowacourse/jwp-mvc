package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class ErrorView implements View {
    private static final Logger logger = LoggerFactory.getLogger(ErrorView.class);
    private static final String DEFAULT_ERROR_VIEW_NAME = "error.jsp";
    private final String viewName;

    public ErrorView(String viewName) {
        this.viewName = viewName;
    }

    public static View defaultErrorView() {
        return new ErrorView(DEFAULT_ERROR_VIEW_NAME);
    }

    @Override
    public void render(Map<String, ?> model, RequestContext requestContext) {
        HttpServletRequest request = requestContext.getHttpServletRequest();
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
        model.forEach(request::setAttribute);

        try {
            requestDispatcher.forward(request, requestContext.getHttpServletResponse());
        } catch (ServletException | IOException e) {
            logger.error("Internal Server Exception : ", e);
            throw new ErrorViewRenderingFailedException();
        }
    }
}
