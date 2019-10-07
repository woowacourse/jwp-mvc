package nextstep.mvc.tobe;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.Map;

public class JsonView implements View {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Writer writer = response.getWriter();

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        if (model.size() > 0) {
            writer.write(OBJECT_MAPPER.writeValueAsString(getValue(model)));
        }
        writer.flush();
        response.flushBuffer();
    }

    private Object getValue(Map<String, ?> model) {
        if (model.size() == 1) {
            return model.values().iterator().next();
        }
        return model;
    }
}
