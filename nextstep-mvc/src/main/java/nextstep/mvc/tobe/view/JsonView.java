package nextstep.mvc.tobe.view;

import nextstep.utils.JsonUtils;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        setContentType(response);
        response.getOutputStream().write(JsonUtils.toJsonString(model).getBytes());
    }

    private void setContentType(HttpServletResponse response) {
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
}
