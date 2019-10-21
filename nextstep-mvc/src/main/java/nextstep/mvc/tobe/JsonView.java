package nextstep.mvc.tobe;

import nextstep.mvc.ViewException;
import nextstep.utils.JsonUtils;
import nextstep.web.support.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;

public class JsonView implements View {
    private static final Logger log = LoggerFactory.getLogger(JsonView.class);

    private JsonView() {
    }

    private static class SingletonHolder {
        private static final JsonView INSTANCE = new JsonView();
    }

    public static JsonView getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        writeJson(model, response);
    }

    private void writeJson(Map<String, ?> model, HttpServletResponse response) {
        toJson(model).ifPresent(json -> {
            log.debug("json: {}", json);

            PrintWriter writer = tryGetWriter(response);
            writer.print(json);
            writer.flush();
            response.setContentLength(json.length());
        });
    }

    private PrintWriter tryGetWriter(HttpServletResponse response) {
        try {
            return response.getWriter();
        } catch (IOException e) {
            throw new ViewException(e);
        }
    }

    private Optional<String> toJson(Map<String, ?> model) {
        if (model.isEmpty()) {
            return Optional.empty();
        }

        Object object = peelOffIfOneElementExist(model);
        return Optional.of(JsonUtils.toJson(object));
    }

    private Object peelOffIfOneElementExist(Map<String, ?> model) {
        if (model.size() == 1) {
            return model.values().stream()
                    .findFirst().get();
        }
        return model;
    }
}
