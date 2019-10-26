package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.view.exception.JsonRenderingFailException;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private static final int MONO = 1;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (model.isEmpty()) {
            return;
        }

        try {
            PrintWriter writer = response.getWriter();
            String json = getJson(model);

            writer.write(json);
        } catch (IOException e) {
            throw new JsonRenderingFailException();
        }
    }

    private String getJson(Map<String, ?> model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return model.size() == MONO
                ? objectMapper.writeValueAsString(getSingleObjectIn(model))
                : objectMapper.writeValueAsString(model);
    }

    private Object getSingleObjectIn(Map<String,?> model) {
        return model.values().iterator().next();
    }
}
