package nextstep.mvc.view;

import nextstep.utils.JsonUtils;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JsonView implements View {
    private static final int OK_STATUS_CODE = 200;
    private static final int CREATED_STATUS_CODE = 201;
    private static final String LOCATION_HEADER = "Location";

    private int status;
    private String location;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(status);

        setLocation(response);
        setBody(model, response);
    }

    public JsonView ok() {
        this.status = OK_STATUS_CODE;
        this.location = null;
        return this;
    }

    public JsonView created(String location) {
        this.status = CREATED_STATUS_CODE;
        this.location = location;
        return this;
    }

    private void setLocation(HttpServletResponse response) {
        if (location != null) {
            response.setHeader(LOCATION_HEADER, location);
        }
    }

    private void setBody(Map<String, ?> model, HttpServletResponse response) throws IOException {
        if (!model.isEmpty()) {
            String content = JsonUtils.parseToJson(model);
            response.getWriter().write(content);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
