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
    private static final int MODEL_NO_BODY_SIZE = 0;
    private static final int MODEL_ONE_SIZE = 1;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String body = convertJson(model);
        logger.debug("model : {}, body : {}", model, body);
        writeBody(response.getWriter(), body);
    }

    private String convertJson(Map<String, ?> model) {
        if (model.size() == MODEL_NO_BODY_SIZE) {
            return "";
        }
        if (model.size() == MODEL_ONE_SIZE) {
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
