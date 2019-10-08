package nextstep.mvc.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        String result = "";
        if (model.isEmpty()) {
            result = "";
        }
        ObjectMapper mapper = new ObjectMapper();
        if (model.size() == 1) {
            result = mapper.writeValueAsString(model.values().toArray()[0]);
        }
        if (model.size() > 1) {
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
        }
        writer.write(result);
        writer.flush();
        writer.close();
    }
}
