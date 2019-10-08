package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.utils.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        final PrintWriter writer = response.getWriter();
        writer.println(convert(model));
        writer.flush();
    }

    private Object convert(final Map<String, ?> model) throws JsonProcessingException {
        if (model.size() == 0) {
            return StringUtils.EMPTY;
        }

        if (model.size() == 1) {
            return OBJECT_MAPPER.writeValueAsString(model.values().toArray()[0]);
        }

        return OBJECT_MAPPER.writeValueAsString(model);
    }
}
