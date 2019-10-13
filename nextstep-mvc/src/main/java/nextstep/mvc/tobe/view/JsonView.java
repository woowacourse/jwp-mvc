package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.RequestContext;
import nextstep.utils.JsonUtils;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {

    @Override
    public void render(Map<String, ?> model, RequestContext requestContext) {
        HttpServletResponse response = requestContext.getHttpServletResponse();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        JsonUtils.writeValue(response, model);
    }
}
