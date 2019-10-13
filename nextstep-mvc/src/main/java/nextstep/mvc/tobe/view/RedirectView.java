package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class RedirectView implements View {
    private static final Logger logger = LoggerFactory.getLogger(RedirectView.class);

    private final String viewName;

    public RedirectView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, RequestContext requestContext) {
        try {
            requestContext.getHttpServletResponse().sendRedirect(viewName);
        } catch (IOException | IllegalStateException e) {
            logger.error("Http Request Exception : ", e);
            throw new RedirectionFailedException();
        }
    }
}
