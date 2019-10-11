package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private final Logger logger = LoggerFactory.getLogger(JsonView.class);
    private static final int MODEL_NO_BODY_SIZE = 0;
    private static final int MODEL_ONE_SIZE = 1;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        writeModel(response.getWriter(), model);
    }

    private void writeModel(PrintWriter writer, Map<String, ?> model) throws IOException {
        Object body = model;
        if (model.size() == MODEL_NO_BODY_SIZE) {
            return;
        }
        if (model.size() == MODEL_ONE_SIZE) {
            String key = model.keySet().iterator().next();
            body = model.get(key);
        }
        writeBody(writer, body);
    }

    private void writeBody(PrintWriter writer, Object body) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(writer, body);
    }
}
