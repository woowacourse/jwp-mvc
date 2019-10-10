package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();

        for (String key : model.keySet()) {
            writer.write(objectMapper.writeValueAsString(model.get(key)));
        }

        writer.flush();
    }
}
