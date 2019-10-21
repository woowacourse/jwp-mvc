package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ObjectMapperException;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {
    public static <T> T toObject(String json, Class<T> clazz) throws ObjectMapperException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static String toJson(Map<String, ?> model) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        if (model.size() == 1) {
            Object value = model.values()
                    .stream()
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);

            return mapper.writeValueAsString(value);
        }

        return mapper.writeValueAsString(model);
    }
}
