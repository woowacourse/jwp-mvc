package nextstep.mvc.tobe.view;

import com.google.gson.Gson;
import nextstep.web.support.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private final Logger logger = LoggerFactory.getLogger(JsonView.class);

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String body = convertJson(model);
        logger.debug("model : {}, body : {}", model, body);
        writeBody(response.getWriter(), body);
    }

    private String convertJson(Map<String, ?> model) {
        if (model.size() == 0) {
            return "";
        }
        if (model.size() == 1) {
            String key = model.keySet().iterator().next();
            return new Gson().toJson(model.get(key));
        }
        return new Gson().toJson(model);
    }

    private void writeBody(PrintWriter writer, String body) {
        writer.write(body);
        writer.flush();
        writer.close();
    }
}
