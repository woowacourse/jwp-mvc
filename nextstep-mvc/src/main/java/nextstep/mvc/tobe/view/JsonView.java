package nextstep.mvc.tobe.view;

import nextstep.utils.JsonUtils;
import nextstep.web.support.MediaType;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        JsonUtils.writeValue(response.getWriter(), modelToObject(model));
    }

    private Object modelToObject(Map<String, ?> model) {
        if (model.isEmpty()) {
            return StringUtils.EMPTY;
        }

        if (model.size() > 1) {
            return model;
        }

        return model.values()
                .iterator()
                .next();
    }
}
