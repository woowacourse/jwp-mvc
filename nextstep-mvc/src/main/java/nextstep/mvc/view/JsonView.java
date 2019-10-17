package nextstep.mvc.view;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.Map;

public class JsonView implements View {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.setVisibility(
                objectMapper.getSerializationConfig()
                            .getDefaultVisibilityChecker()
                            .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                            .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                            .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
        );
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        final Writer writer = res.getWriter();
        switch (model.size()) {
            case 0:
                return;
            case 1:

                writer.write(objectMapper.writeValueAsString(model.values().stream().findAny().get()));
                break;
            default:
                writer.write(objectMapper.writeValueAsString(model));
        }
        writer.flush();
        writer.close();
    }
}