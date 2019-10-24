package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {

    public static final int UNIQUE = 1;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            PrintWriter writer = response.getWriter();
            String json = getJsonString(model);

            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getJsonString(Map<String, ?> model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return model.size() == UNIQUE
                ? objectMapper.writeValueAsString(model.values().iterator().next())
                : objectMapper.writeValueAsString(model);
    }
}
