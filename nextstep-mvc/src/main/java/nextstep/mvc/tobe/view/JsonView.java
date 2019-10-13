package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        Set<String> modelKeys = model.keySet();
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter out = response.getWriter();
        String body = "";
        if (modelKeys.size() <= 1) {
            for (String key : modelKeys) {
                body = objectMapper.writeValueAsString(model.get(key));
                ;
            }
        } else {
            body = objectMapper.writeValueAsString(model);
        }
        out.write(body);
    }
}
