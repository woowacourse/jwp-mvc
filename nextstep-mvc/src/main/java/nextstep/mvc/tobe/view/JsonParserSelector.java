package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.view.exception.InvalidModelSizeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public enum JsonParserSelector {
    BLANK(size -> size == 0, (model) -> ""),
    ONE_SIZE(size -> size == 1, model -> {
        ObjectMapper objectMapper = new ObjectMapper();
        List<?> temp = new ArrayList<>(model.values());
        return objectMapper.writeValueAsString(temp.get(0));
    }),
    MANY_SIZE(size -> size >= 2, model -> {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(model);
    });

    private final Predicate<Integer> sizeChecker;
    private final ModelToJsonParser<Map<String, ?>, String> jsonParser;

    JsonParserSelector(Predicate<Integer> sizeChecker, ModelToJsonParser<Map<String, ?>, String> jsonParser) {
        this.sizeChecker = sizeChecker;
        this.jsonParser = jsonParser;
    }

    public static ModelToJsonParser<Map<String, ?>, String> select(final int size) {
        return Arrays.stream(JsonParserSelector.values())
                .filter(jsonParserSelector -> jsonParserSelector.sizeChecker.test(size))
                .findFirst()
                .orElseThrow(InvalidModelSizeException::new)
                .jsonParser;
    }
}
