package nextstep.mvc.tobe;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private static final int SINGLE_MODEL_SIZE = 1;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        if(model.isEmpty()) {
            return;
        }

        if(model.size() == SINGLE_MODEL_SIZE) {
            objectMapper.writeValue(response.getWriter(), model.values().toArray()[0]);
        }

        objectMapper.writeValue(response.getWriter(), model);
    }
}
