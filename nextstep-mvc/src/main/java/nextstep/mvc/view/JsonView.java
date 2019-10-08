package nextstep.mvc.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    private static final int EMPTY_MODEL_SIZE = 0;
    private static final int SINGLE_DATA_MODEL_SIZE = 1;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        if (model.size() != EMPTY_MODEL_SIZE) {
            String content = objectToJson(model, objectMapper);
            response.getWriter().write(content);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    private String objectToJson(Map<String, ?> model, ObjectMapper objectMapper) throws JsonProcessingException {
        return model.size() == SINGLE_DATA_MODEL_SIZE ?
                objectMapper.writeValueAsString(getSingleData(model)) : objectMapper.writeValueAsString(model);
    }

    private Object getSingleData(Map<String, ?> model) {
        return model.values().stream()
                .findFirst()
                .orElseThrow(NotSingleDataException::new);
    }
}
