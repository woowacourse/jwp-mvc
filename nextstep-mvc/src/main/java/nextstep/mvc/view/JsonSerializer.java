package nextstep.mvc.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;
import java.util.Map;

@FunctionalInterface
public interface JsonSerializer {
    void serialize(Map<String, ?> model, ObjectMapper mapper, PrintWriter writer) throws JsonProcessingException;
}
