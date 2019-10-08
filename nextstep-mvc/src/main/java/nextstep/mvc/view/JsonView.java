package nextstep.mvc.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    private static final int ONE_DATA_TO_MODEL_SIZE = 1;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        if (model.size() != 0) {
            String content = getContent(model, objectMapper);
            response.getWriter().write(content);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    private String getContent(Map<String, ?> model, ObjectMapper objectMapper) throws JsonProcessingException {
        return model.size() == ONE_DATA_TO_MODEL_SIZE ?
                objectToString(model, objectMapper) : objectToJson(model, objectMapper);
    }

    private String objectToString(Map<String, ?> model, ObjectMapper objectMapper) throws JsonProcessingException {
        Object value = model.values().stream()
                .findFirst()
                .orElseThrow(ObjectToStringException::new);
        return objectMapper.writeValueAsString(value);
    }

    private String objectToJson(Map<String, ?> model, ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(model);
    }
}
