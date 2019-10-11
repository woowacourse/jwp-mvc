package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.View;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter out = response.getWriter();

        if (model.size() == 1) {
            out.println(objectMapper.writeValueAsString(model.values().toArray()[0]));
        } else if (model.size() > 1) {
            out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(model));
        }

        out.flush();
        out.close();
    }
}
