package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.RequestContext;
import nextstep.web.support.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private static final Logger logger = LoggerFactory.getLogger(JsonView.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void render(Map<String, ?> model, RequestContext requestContext) {
        HttpServletResponse response = requestContext.getHttpServletResponse();

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(convertData(model));
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            logger.error("Exception : {}", e);
            throw new JsonWritingFailedException();
        }
    }

    private String convertData(Map<String, ?> model) throws IOException {
        if (model.isEmpty()) {
            return StringUtils.EMPTY;
        }

        if (model.size() == 1) {
            Object value = model.values()
                    .iterator()
                    .next();
            return OBJECT_MAPPER.writeValueAsString(value);
        }

        return OBJECT_MAPPER.writeValueAsString(model);
    }
}
