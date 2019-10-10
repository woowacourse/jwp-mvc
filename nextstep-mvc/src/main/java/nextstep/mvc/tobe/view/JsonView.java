package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.View;
import nextstep.web.support.MediaType;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class JsonView implements View {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String json = modelParse(model);
        setJsonResponseHeader(response, json);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(request.getRequestURI());
        requestDispatcher.forward(request, response);
    }

    private String modelParse(Map<String, ?> model) {
        ModelSize modelSize = ModelSize.of(model.size());
        Function<Map<String, ?>, String> jsonParser = JsonParser.getJsonParser(modelSize);

        return jsonParser.apply(model);
    }

    private void setJsonResponseHeader(HttpServletResponse response, String json) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setContentLength(json.length());
        response.getWriter().write(json);
    }
}
