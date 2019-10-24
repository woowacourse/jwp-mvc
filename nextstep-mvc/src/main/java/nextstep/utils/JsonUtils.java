package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ObjectMapperException;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setVisibility(MAPPER.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
    }

    public static <T> T toObject(String json, Class<T> clazz) throws ObjectMapperException {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (final IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static String toJson(final Map<String, ?> model) throws JsonProcessingException {
        if (hasOneElement(model)) {
            return MAPPER.writeValueAsString(model.values().toArray()[0]);
        }
        return MAPPER.writeValueAsString(model);
    }

    private static boolean hasOneElement(final Map<String, ?> model) {
        return Objects.nonNull(model) && model.size() == 1;
    }
}
