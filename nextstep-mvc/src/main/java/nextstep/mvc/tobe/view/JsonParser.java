package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public class JsonParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Map<ModelSize, Function<Map<String, ?>, String>> parsers = new HashMap<>();

    static {
        parsers.put(ModelSize.BLANK, model -> "");
        parsers.put(ModelSize.ONE_SIZE, model -> {
            TreeMap<String, ?> map = new TreeMap<>(model);
            String result = "";
            try {
                result = objectMapper.writeValueAsString(map.get(map.firstKey()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return result;
        });
        parsers.put(ModelSize.MANY_SIZE, model -> {
            String result = "";
            try {
                result = objectMapper.writeValueAsString(model);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return result;
        });
    }

    static Function<Map<String, ?>, String> getJsonParser(ModelSize modelSize) {
        if (modelSize == null) {
            throw new IllegalArgumentException("modelSize가 null이 되면 안됩니다.");
        }

        return parsers.get(modelSize);
    }
}
