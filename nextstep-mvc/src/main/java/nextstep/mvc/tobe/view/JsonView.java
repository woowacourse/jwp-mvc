package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private static final int SINGLE_VALUE = 1;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();

        if (hasSingleValue(model)) {
            writer.write(mapper.writeValueAsString(model.values().iterator().next()));
        }
        if (hasMultiValues(model)) {
            writeMultiValues(model, mapper, writer);
        }
        writer.flush();
    }

    private void writeMultiValues(Map<String, ?> model, ObjectMapper mapper, PrintWriter writer) throws JsonProcessingException {
        for (String key : model.keySet()) {
            writer.write(mapper.writeValueAsString(model.get(key)));
        }
    }

    private boolean hasMultiValues(Map<String, ?> model) {
        return model.size() > SINGLE_VALUE;
    }

    private boolean hasSingleValue(Map<String, ?> model) {
        return model.size() == SINGLE_VALUE;
    }
}
