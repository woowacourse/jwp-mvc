package nextstep.mvc.tobe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static nextstep.web.support.MediaType.APPLICATION_JSON_UTF8_VALUE;

public class JsonView implements View {
    private static final Logger logger = LoggerFactory.getLogger(JsonView.class);
    private static final String BLANK = "";

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_JSON_UTF8_VALUE);
        String content = getContent(model);
        createResponse(response, content);
    }

    private String getContent(Map<String, ?> model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        int modelSize = model.size();

        if(modelSize == 0){
            return BLANK;
        }
        else if(modelSize == 1) {
            return objectMapper.writeValueAsString(
                    model.values().stream().findFirst().get());
        }
            return objectMapper.writeValueAsString(model);
    }

    private void createResponse(HttpServletResponse response, String content) throws IOException {
        PrintWriter printWriter = response.getWriter();
        printWriter.print(content);
        printWriter.flush();
    }
}
