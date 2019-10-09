package nextstep.mvc.tobe;

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
        PrintWriter responseWriter = response.getWriter();
        for (Map.Entry<String, ?> modelEntry : model.entrySet()) {
            responseWriter.write(JsonUtils.toJson(modelEntry.getValue()));
        }
        responseWriter.flush();
    }
}
