package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.view.exception.InvalidModelSizeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public enum JsonParserSelector {
    BLANK(size -> size == 0, (model) -> Constants.BLANK),
    ONE_SIZE(size -> size == 1, model -> {
        List<?> temp = new ArrayList<>(model.values());
        return Constants.objectMapper.writeValueAsString(temp.get(Constants.FIRST_INDEX));
    }),
    MANY_SIZE(size -> size >= 2, Constants.objectMapper::writeValueAsString);

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

class Constants {
    static final ObjectMapper objectMapper = new ObjectMapper();
    static final String BLANK = "";
    static final int FIRST_INDEX = 0;
}
