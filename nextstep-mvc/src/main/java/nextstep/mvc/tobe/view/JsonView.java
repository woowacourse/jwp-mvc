package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (model.size() != 0) {
            response.getWriter().write(ObjectToJson(model));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    private String ObjectToJson(Map<String, ?> model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        if (model.size() == 1) {
            return convertOneData(model, objectMapper);
        }

        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(model);
    }

    private String convertOneData(Map<String, ?> model, ObjectMapper objectMapper) throws JsonProcessingException {
        Object object = model.values().stream()
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        return objectMapper.writeValueAsString(object);
    }
}
