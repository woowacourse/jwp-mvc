package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.internal.lang3.StringUtils;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();

        Object body = getBody(model);
        objectMapper.writeValue(writer, body);
    }

    private Object getBody(Map<String, ?> model) {
        if (model.isEmpty()) {
            return StringUtils.EMPTY;
        }

        if (model.size() == 1) {
            return model.entrySet()
                    .stream()
                    .findAny()
                    .orElseThrow(IllegalArgumentException::new)
                    .getValue();
        }

        return model;
    }
}
