package nextstep.mvc.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Writer writer = response.getWriter();
        writeModel(objectMapper, writer, model);
    }

    private void writeModel(ObjectMapper objectMapper, Writer writer, Map<String, ?> model) throws Exception {
        if (model.size() == 1) {
            objectMapper.writeValue(writer, model.values().toArray()[0]);
        }
        if (model.size() >= 2) {
            objectMapper.writeValue(writer, model);
        }
    }
}
