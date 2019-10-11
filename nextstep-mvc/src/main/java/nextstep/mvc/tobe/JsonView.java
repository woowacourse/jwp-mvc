package nextstep.mvc.tobe;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private static final Logger log = LoggerFactory.getLogger(JsonView.class);

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter writer = response.getWriter();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));

        if (model.isEmpty()) {
            return;
        }

        String json = "";
        if (model.size() == 1) {
            json = objectMapper.writeValueAsString(getValue(model));
        }

        if(model.size() > 2) {
            json = objectMapper.writeValueAsString(model);
        }

        writer.write(json);
        writer.flush();
        writer.close();
    }

    private Object getValue(Map<String, ?> model) {
        return model.values()
                .iterator()
                .next();
    }
}
