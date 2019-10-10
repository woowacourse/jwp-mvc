package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.RequestContext;
import nextstep.utils.JsonUtils;
import nextstep.web.support.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private static final Logger logger = LoggerFactory.getLogger(JsonView.class);

    @Override
    public void render(Map<String, ?> model, RequestContext requestContext) {
        HttpServletResponse response = requestContext.getHttpServletResponse();

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JsonUtils.toJson(model));
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            logger.error("Http Request Exception : ", e);
            throw new JsonWritingFailedException();
        }
    }
}
