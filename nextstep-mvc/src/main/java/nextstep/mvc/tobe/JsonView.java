package nextstep.mvc.tobe;

import nextstep.utils.JsonUtils;
import nextstep.web.support.MediaType;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter responseWriter = response.getWriter();
        JsonUtils.toJson(responseWriter, getJsonModel(model));
        responseWriter.flush();
    }

    private Object getJsonModel(Map<String, ?> model) {
        if (model.isEmpty()) {
            return StringUtils.EMPTY;
        }

        if (model.size() == 1) {
            return getFirst(model);
        }

        return model;
    }

    private Object getFirst(Map<String, ?> model) {
        return model.values().stream().findFirst().get();
    }
}
