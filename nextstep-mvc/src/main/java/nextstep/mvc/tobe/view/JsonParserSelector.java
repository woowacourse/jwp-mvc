package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.view.exception.InvalidModelSizeException;

import java.util.*;

public class JsonParserSelector {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Map<ModelSize, ModelToJsonParser<Map<String, ?>, String>> parsers = new HashMap<>();

    private static final String BLANK = "";
    private static final int FIRST_VALUE_INDEX = 0;

    static {
        parsers.put(ModelSize.BLANK, model -> BLANK);
        parsers.put(ModelSize.ONE_SIZE, model -> {
            List<?> temp = new ArrayList<>(model.values());
            return objectMapper.writeValueAsString(temp.get(FIRST_VALUE_INDEX));
        });
        parsers.put(ModelSize.MANY_SIZE, objectMapper::writeValueAsString);
    }

    static ModelToJsonParser<Map<String, ?>, String> getJsonParser(ModelSize modelSize) {
        if (modelSize == null) {
            throw new InvalidModelSizeException("모델사이즈가 null입니다. 적절한 사이즈를 넣어주세요.");
        }

        return parsers.get(modelSize);
    }
}
