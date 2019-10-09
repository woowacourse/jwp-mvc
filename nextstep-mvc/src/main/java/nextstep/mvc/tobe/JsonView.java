package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

import static nextstep.utils.JsonUtils.OBJECT_MAPPER;
import static nextstep.web.support.MediaType.APPLICATION_JSON_UTF8_VALUE;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType(APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();

        if (hasModel(model)) {
            writer.write(OBJECT_MAPPER.writeValueAsString(getValue(model)));
        }

        writer.flush();
    }

    private boolean hasModel(Map<String, ?> model) {
        return model.size() > 0;
    }

    private Object getValue(Map<String, ?> model) {
        if (model.size() == 1) {
            return model.values()
                    .stream()
                    .findAny()
                    .orElseThrow(IllegalArgumentException::new);
        }
        return model;
    }

}
