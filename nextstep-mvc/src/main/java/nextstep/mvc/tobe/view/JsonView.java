package nextstep.mvc.tobe.view;

import nextstep.utils.JsonUtils;
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

        if (model.isEmpty()) {
            renderNoElement(writer);
            return;
        }
        if (model.size() == 1) {
            renderOneElement(model, writer);
            return;
        }
        renderOverOneElement(model, writer);
    }

    private void renderNoElement(PrintWriter writer) {
        writer.println();
        writer.flush();
    }

    private void renderOneElement(Map<String, ?> model, PrintWriter writer) {
        Object value = model.entrySet()
                .stream()
                .findAny()
                .orElseThrow(IllegalArgumentException::new)
                .getValue();

        writer.println(JsonUtils.toJson(value));
        writer.flush();
    }

    private void renderOverOneElement(Map<String, ?> model, PrintWriter writer) {
        writer.println(JsonUtils.toJson(model));
        writer.flush();
    }
}
