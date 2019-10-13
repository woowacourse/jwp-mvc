package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import nextstep.mvc.tobe.View;
import nextstep.web.support.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    private static final Logger logger = LoggerFactory.getLogger(JsonView.class);


    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = modelParse(model);

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(body);
        response.getWriter().flush();
    }

    private String modelParse(Map<String, ?> model) {
        ModelToJsonParser<Map<String, ?>, String> jsonParser = JsonParserSelector.select(model.size());

        try {
            return jsonParser.parse(model);
        } catch (JsonProcessingException e) {
            logger.error("don't parse model to json: {}", e.getMessage());
            return "{\"message\": \"don't parse model to json\"}";
        }
    }
}
