package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JsonView implements View {
    public static final int SINGLE = 1;
    private static final Logger log = LoggerFactory.getLogger(JsonView.class);

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();

        if (model.values().isEmpty()) {
            return;
        }

        if (singleSize(model)) {
            String json = objectMapper.writeValueAsString(model.values().toArray()[0]);
            write(response, json);
            return;
        }

        String json = objectMapper.writeValueAsString(model);
        write(response, json);
    }

    private void write(HttpServletResponse response, String json) throws IOException {
        log.debug("written json value: {}", json);
        response.getWriter().write(json);
        response.getWriter().flush();
    }

    private boolean singleSize(Map<String, ?> model) {
        return model.values().size() == SINGLE;
    }
}
