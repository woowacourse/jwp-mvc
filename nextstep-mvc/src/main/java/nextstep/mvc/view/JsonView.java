package nextstep.mvc.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        switch (model.size()) {
            case 1:
                objectMapper.writeValue(res.getWriter(), model.values().toArray()[0]);
            case 0:
                return;
            default:
                objectMapper.writeValue(res.getWriter(), model);
        }
    }
}