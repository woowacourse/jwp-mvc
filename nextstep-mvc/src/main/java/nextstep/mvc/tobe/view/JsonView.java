package nextstep.mvc.tobe.view;

import nextstep.utils.JsonUtils;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();

        if (isModelEmpty(model)) {
            return;
        }

        responseJson(model, writer);
    }

    private boolean isModelEmpty(Map<String, ?> model) {
        return model.size() == 0;
    }

    private void responseJson(Map<String, ?> model, PrintWriter writer) throws IOException {
        if (model.size() == 1) {
            String singleValue = JsonUtils.objectMapper.writeValueAsString(model.values().toArray()[0]);
            writer.println(singleValue);
            writer.flush();
            return;
        }

        JsonUtils.objectMapper.writeValue(writer, model);
        writer.flush();
    }
}