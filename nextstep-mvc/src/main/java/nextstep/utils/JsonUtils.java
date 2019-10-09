package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.exception.ObjectMapperException;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {
    public static <T> T toObject(String json, Class<T> clazz) throws ObjectMapperException {
        try {
            ObjectMapper objectMapper = getObjectMapper();
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static <T> T toObject(InputStream inputStream, Class<T> clazz) throws ObjectMapperException {
        try {
            ObjectMapper objectMapper = getObjectMapper();
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
    }
}
