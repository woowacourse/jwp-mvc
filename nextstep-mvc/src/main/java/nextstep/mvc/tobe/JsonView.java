package nextstep.mvc.tobe;

import nextstep.web.support.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

import static nextstep.utils.JsonUtils.OBJECT_MAPPER;

public class JsonView implements View {
    private static final Logger log = LoggerFactory.getLogger(JsonView.class);
    private static final int ONE_MODEL = 1;

    @Override
    public void render(Map<String, ?> models, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        PrintWriter responseWriter = response.getWriter();

        if (!models.isEmpty()) {
            responseWriter.write(OBJECT_MAPPER.writeValueAsString(getModelValues(models)));
        }

        responseWriter.flush();
    }

    private Object getModelValues(Map<String,?> models) {
        if (models.size() == ONE_MODEL) {
            return models.values().toArray()[0];
        }
        return models;
    }

    @Override
    public String getName() {
        return null;
    }
}
