package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    private static final int EMPTY_DATA = 0;
    private static final int SINGLE_DATA = 1;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (model.size() != EMPTY_DATA) {
            response.getWriter().write(ObjectToJson(model));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    private String ObjectToJson(Map<String, ?> model) throws JsonProcessingException {
        Object data = model;
        if (model.size() == SINGLE_DATA) {
            data = getSingleData(model);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(data);
    }

    private Object getSingleData(Map<String, ?> model) {
        return model.values().stream()
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
