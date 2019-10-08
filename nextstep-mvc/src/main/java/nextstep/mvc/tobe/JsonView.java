package nextstep.mvc.tobe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.getWriter().write(writeContent(model));
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    private String writeContent(final Map<String, ?> model) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        if (model.size() == 1) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model.values().toArray()[0]);
        }
        if (model.size() > 1) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
        }

        return "";
    }
}
