package nextstep.mvc.tobe;

import nextstep.utils.JsonUtils;
import nextstep.web.support.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private static final Logger log = LoggerFactory.getLogger(JsonView.class);

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter writer = response.getWriter();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (model.isEmpty()) {
            return;
        }

        String json = JsonUtils.toJson(getValues(model));
        log.debug("json data: {}", json);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    private Object getValues(Map<String, ?> model) {
        if (model.size() == 1) {
            return getValue(model);
        }
        return model;
    }

    private Object getValue(Map<String, ?> model) {
        return model.values()
                .iterator()
                .next();
    }
}
