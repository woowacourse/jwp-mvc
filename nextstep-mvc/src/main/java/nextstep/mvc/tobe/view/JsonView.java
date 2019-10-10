package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private static final int ONE_ELEMENT = 1;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (model.isEmpty()) {
            return;
        }
        writeModel(model, response);
    }

    private void writeModel(Map<String, ?> model, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        Object value = getValueFrom(model);

        writer.write(objectMapper.writeValueAsString(value));
        writer.flush();
    }

    private Object getValueFrom(Map<String, ?> model) {
        if (model.size() == ONE_ELEMENT) {
            Map.Entry<String, ?> entry = model.entrySet().iterator().next();
            return entry.getValue();
        }
        return model;
    }
}
