package nextstep.mvc.tobe;

import nextstep.utils.JsonUtils;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        if (isEmptyModel(model)) {
            return;
        }
        response.getWriter().write(JsonUtils.toJson(model));
    }

    private boolean isEmptyModel(final Map<String, ?> model) {
        return Objects.isNull(model) || model.isEmpty();
    }
}
