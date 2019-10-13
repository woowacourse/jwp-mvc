package nextstep.mvc.tobe.view;

import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static nextstep.utils.JsonUtils.toJson;

public class JsonView implements View {
    private String viewName;

    public JsonView() {
    }

    public JsonView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(toJson(model));
    }
}
