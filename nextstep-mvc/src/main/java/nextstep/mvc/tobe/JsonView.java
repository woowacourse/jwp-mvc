package nextstep.mvc.tobe;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static nextstep.web.support.MediaType.APPLICATION_JSON_UTF8_VALUE;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_JSON_UTF8_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        model.forEach((k, v) -> {
            try {
                objectMapper.writeValue(response.getOutputStream(), model.get(k));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
