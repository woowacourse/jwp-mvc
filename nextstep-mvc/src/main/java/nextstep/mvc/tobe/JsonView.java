package nextstep.mvc.tobe;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (model != null && model.size() > 0) {
            if (model.size() > 1) {
                mapper.writeValue(response.getWriter(), model);
            }
            mapper.writeValue(response.getWriter(), model.values().toArray()[0]);
        }
    }
}
