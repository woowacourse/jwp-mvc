package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import nextstep.mvc.tobe.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    private static final Logger logger = LoggerFactory.getLogger(JsonView.class);

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResponseEntity responseEntity = modelParse(model);
        responseEntity.setResponseHeader(response);

        response.getWriter().flush();
    }

    private ResponseEntity modelParse(Map<String, ?> model) {
        ModelSize modelSize = ModelSize.of(model.size());
        ResponseEntity responseEntity = null;

        try {
            String body = JsonParserSelector.getJsonParser(modelSize).parse(model);
            responseEntity = ResponseEntity.ok(body);
        } catch (JsonProcessingException e) {
            logger.error("don't parse model to json: {}", e.getMessage());
            responseEntity = ResponseEntity.badRequest("{\"message\": \"don't parse model to json\"");
        }

        return responseEntity;
    }
}
