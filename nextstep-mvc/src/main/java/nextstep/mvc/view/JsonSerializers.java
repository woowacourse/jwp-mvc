package nextstep.mvc.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.ObjectMapperException;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

public enum JsonSerializers {
    EMPTY(Map::isEmpty, (model, mapper, writer) -> writer.print("")),
    ONE(map -> map.size() == 1, (model, mapper, writer) -> writer.print(mapper.writeValueAsString(model.values().toArray()[0]))),
    DEFAULT(map -> map.size() > 1, (model, mapper, writer) -> writer.print(mapper.writeValueAsString(model)));

    private Predicate<Map<String, ?>> condition;
    private JsonSerializer serializer;

    private JsonSerializers(Predicate<Map<String, ?>> condition, JsonSerializer serializer) {
        this.condition = condition;
        this.serializer = serializer;
    }

    public static void serialize(Map<String, ?> model, ObjectMapper mapper, PrintWriter writer) {
        try {
            JsonSerializers.of(model).serializer.serialize(model, mapper, writer);
        } catch (JsonProcessingException e) {
            throw new ObjectMapperException();
        }
    }

    public static JsonSerializers of(Map<String, ?> model) {
        return Arrays.stream(JsonSerializers.values())
                .filter(jsonSerializers -> jsonSerializers.condition.test(model))
                .findAny()
                .orElseThrow(ObjectMapperException::new);
    }
}
