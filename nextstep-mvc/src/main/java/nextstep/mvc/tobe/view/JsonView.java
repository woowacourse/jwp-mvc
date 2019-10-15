package nextstep.mvc.tobe.view;

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

        if (model.size() > 0) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            OBJECT_MAPPER.writeValue(writer, getValue(model));
        }

        writer.close();
        response.flushBuffer();
    }

    private Object getValue(Map<String, ?> model) {
        if (model.size() == 1) {
            return model.values()
                    .iterator()
                    .next();
        }
        return model;
    }
}
