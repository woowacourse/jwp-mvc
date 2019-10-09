package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

import static nextstep.web.support.MediaType.APPLICATION_JSON_UTF8_VALUE;

public class JsonView implements View {
    private static final int SINGLE_ELEMENT = 1;
    private static final int EMPTY = 0;
    private static final String BLANK = "";

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_JSON_UTF8_VALUE);

        String abc = convertObjectToString(model);

        PrintWriter writer = response.getWriter();
        writer.println(abc);
    }

    private String convertObjectToString(Map<String, ?> model) throws JsonProcessingException {
        int size = model.size();
        if (size == EMPTY) {
            return BLANK;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(handleAccordingToSize(model));
    }

    private Object handleAccordingToSize(Map<String, ?> model) {
        return model.size() == SINGLE_ELEMENT ? model.values().stream().findAny().get() : model;
    }
}
