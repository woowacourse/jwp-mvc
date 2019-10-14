package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        PrintWriter writer = response.getWriter();
        writer.println(convert(model));
        writer.flush();
    }

    private String convert(final Map<String, ?> model) throws JsonProcessingException {
        if (isModelEmpty(model)) {
            return StringUtils.EMPTY;
        }
        if (hasOneObject(model)) {
            return OBJECT_MAPPER.writeValueAsString(model.values().toArray()[0]);
        }
        return OBJECT_MAPPER.writeValueAsString(model);
    }

    private boolean isModelEmpty(final Map<String, ?> model) {
        return model.isEmpty();
    }

    private boolean hasOneObject(final Map<String, ?> model) {
        return model.size() == 1;
    }
}
