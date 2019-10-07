package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.RequestContext;
import nextstep.web.support.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class ErrorView implements View {
    private static final Logger logger = LoggerFactory.getLogger(ErrorView.class);
    public static final String HTTP_STATUS_KEY = "httpStatus";
    private final String viewName;

    public ErrorView(String viewName) {
        this.viewName = viewName;
    }

    private ErrorView() {
        this.viewName = "default";
    }

    public static View defaultErrorView() {
        return new ErrorView();
    }

    @Override
    public void render(Map<String, ?> model, RequestContext requestContext) {
        HttpStatus httpStatus = (HttpStatus) model.get(HTTP_STATUS_KEY);

        try {
            requestContext.getHttpServletResponse().sendError(httpStatus.getStatus(), httpStatus.getPhrase());
        } catch (IOException e) {
            logger.error("Internal Server Exception : ", e);
            throw new ErrorViewRenderingFailedException();
        }
    }
}
