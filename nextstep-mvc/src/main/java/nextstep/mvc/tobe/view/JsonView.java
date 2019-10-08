package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JsonView implements View {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        setContentType(response);
        if (model.size() == 0) {

        }
        else if (model.size() == 1) {
            writeOnce(model, response, objectMapper);
        }
        else {
            objectMapper.writeValue(response.getOutputStream(), model);
        }
    }

    private void writeOnce(Map<String, ?> model, HttpServletResponse response, ObjectMapper objectMapper) throws IOException {
        objectMapper.writeValue(response.getOutputStream(), model.get("car"));
    }

    private void setContentType(HttpServletResponse response) {
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
}
