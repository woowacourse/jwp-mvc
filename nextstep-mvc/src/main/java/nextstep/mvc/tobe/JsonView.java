package nextstep.mvc.tobe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (model.isEmpty()) {
            return;
        }

        String json = toJson(model);
        logger.debug("Json : {}", json);
        response.getWriter().write(json);
    }

    private String toJson(Map<String, ?> model) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        if (model.size() == 1) {
            Object value = model.values()
                    .stream()
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);

            return mapper.writeValueAsString(value);
        }

        return mapper.writeValueAsString(model);
    }
}
