package nextstep.mvc.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String modelAsString = getModelAsString(model, objectMapper);
        response.getWriter().write(modelAsString);
    }

    private String getModelAsString(Map<String, ?> model, ObjectMapper objectMapper) throws Exception {
        if (model.size() == 1) {
            Object value = model.values().toArray()[0];
            return objectMapper.writeValueAsString(value);
        }
        if (model.size() >= 2) {
            return objectMapper.writeValueAsString(model);
        }
        return "";
    }
}
