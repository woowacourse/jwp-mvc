package nextstep.mvc.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JsonView implements View {
    private static final int EMPTY_MODEL_SIZE = 0;
    private static final int SINGLE_DATA_MODEL_SIZE = 1;
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
        if (model.size() != EMPTY_MODEL_SIZE) {
            String content = objectToJson(model);
            response.getWriter().write(content);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    private String objectToJson(Map<String, ?> model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return model.size() == SINGLE_DATA_MODEL_SIZE ?
                objectMapper.writeValueAsString(getSingleData(model)) : objectMapper.writeValueAsString(model);
    }

    private Object getSingleData(Map<String, ?> model) {
        return model.values()
                .iterator()
                .next();
    }
}
