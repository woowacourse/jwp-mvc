package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private static final int SINGLE_VALUE = 1;
    private static final int MULTI_VALUE = 2;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        if (model.isEmpty()) {
            return;
        }
        if (hasSingleValue(model)) {
            writeSingleValue(model, writer, objectMapper);
            return;
        }
        if (hasMultiValue(model)) {
            objectToJson(model, writer, objectMapper);
            writer.flush();
        }
    }

    private boolean hasSingleValue(Map<String, ?> model) {
        return model.size() == SINGLE_VALUE;
    }

    private boolean hasMultiValue(Map<String, ?> model) {
        return model.size() >= MULTI_VALUE;
    }

    private void writeSingleValue(Map<String, ?> model, PrintWriter writer, ObjectMapper objectMapper) throws IOException {
        Object singleValue = model.values()
                .iterator()
                .next();
        writer.write(objectMapper.writeValueAsString(singleValue));
        writer.flush();
    }

    private void objectToJson(Map<String, ?> model, PrintWriter writer, ObjectMapper objectMapper) throws JsonProcessingException {
        writer.write(objectMapper.writeValueAsString(model));
    }
}
