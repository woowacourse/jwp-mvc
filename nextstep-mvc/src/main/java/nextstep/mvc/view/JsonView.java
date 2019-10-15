package nextstep.mvc.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.function.Function;

public class JsonView implements View {

    private static final Logger log = LoggerFactory.getLogger(JsonView.class);
    private static final String EMPTY_STRING = "";
    private static final String JSON_PROCESSING_ERROR = "적절하지 않음";
    private static final int JSON_CRITERIA = 1;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = EMPTY_STRING;
        ObjectMapper mapper = new ObjectMapper();
        result = when(model.size() < JSON_CRITERIA, result, writeEmptyModel());
        result = when(model.size() == JSON_CRITERIA, result, writeOneModel(model, mapper));
        result = when(model.size() > JSON_CRITERIA, result, writeModels(model, mapper));
        write(response, result);
    }

    private String when(boolean condition, String result, Function<String, String> function) {
        if (condition) {
            return function.apply(result);
        }
        return result;
    }

    private Function<String, String> writeEmptyModel() {
        return s -> EMPTY_STRING;
    }

    private Function<String, String> writeOneModel(Map<String, ?> model, ObjectMapper mapper) {
        return s -> {
            try {
                return mapper.writeValueAsString(model.values().toArray()[0]);
            } catch (JsonProcessingException e) {
                log.debug("JsonProcessing - {}", e.getMessage());
                e.printStackTrace();
            }
            throw new IllegalArgumentException(JSON_PROCESSING_ERROR);
        };
    }

    private Function<String, String> writeModels(Map<String, ?> model, ObjectMapper mapper) {
        return s -> {
            try {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
            } catch (JsonProcessingException e) {
                log.debug("JsonProcessing - {}", e.getMessage());
                e.printStackTrace();
            }
            throw new IllegalArgumentException(JSON_PROCESSING_ERROR);
        };
    }

    private void write(HttpServletResponse response, String result) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(result);
        writer.flush();
        writer.close();
    }
}
