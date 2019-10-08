package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String modelAsString = "";
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (model.size() < 1) {
            return;
        }
        if (model.size() == 1) {
            Object value = model.values().toArray()[0];
            modelAsString = objectMapper.writeValueAsString(value);
        }
        if (model.size() >= 2) {
            modelAsString = objectMapper.writeValueAsString(model);
        }

        response.getWriter().write(modelAsString);
    }
}
