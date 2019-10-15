package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ObjectMapperException;

import java.io.IOException;

public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T toObject(String json, Class<T> clazz) throws ObjectMapperException {
        try {
            ObjectMapper objectMapper = getInstance();
            objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static String toJson(Object model) throws ObjectMapperException, JsonProcessingException {
        ObjectMapper objectMapper = getInstance();
        return objectMapper.writeValueAsString(model);
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }
}
